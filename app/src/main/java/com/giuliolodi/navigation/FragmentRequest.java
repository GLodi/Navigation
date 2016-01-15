package com.giuliolodi.navigation;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Location;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FragmentRequest extends Fragment {

    public String requestString;
    public TextView requestCity;
    public EditText editText;
    public Double LATITUDE;
    public Double LONGITUDE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the view
        final View v = inflater.inflate(R.layout.fragment_request, container, false);
        final Context context = getActivity().getBaseContext();

        // Reference the view
        requestCity = (TextView) v.findViewById(R.id.request_city);
        editText = (EditText) v.findViewById(R.id.edit_text);

        // Set listener for enter key
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    requestString = editText.getText().toString();
                    if (requestString.isEmpty()) {
                        requestCity.setText("Please insert city name");
                    }
                    else {
                        geoCoder();
                        getWeather(v);
                        getWeatherWeek(v, context);
                    }
                    return true;
                }
                return false;
            }
        });
        return v;

    }

    public void geoCoder() {
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            Address address = null;
            for (int i = 0; i < 5; i++) {
                if (address == null)
                    address = geocoder.getFromLocationName(requestString, 1).get(0);
            }
            if (address == null) {
                Log.d("TAG", "address is null");
            }
            LATITUDE = address.getLatitude();
            LONGITUDE = address.getLongitude();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWeather(View v) {

        requestCity = (TextView) v.findViewById(R.id.request_city);
        editText = (EditText) v.findViewById(R.id.edit_text);

        // Initializing Weather Client Builder
        try {

            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getCurrentCondition(new WeatherRequest(LONGITUDE, LATITUDE), new WeatherClient.WeatherEventListener() {
                @Override
                public void onWeatherRetrieved(CurrentWeather weather) {
                    float currentTemp = weather.weather.temperature.getTemp();
                    requestCity.setText(weather.weather.location.getCity() + " " + Float.toString(currentTemp) + "°C ");
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {

                }

                @Override
                public void onConnectionError(Throwable t) {

                }
            });
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void getWeatherWeek(View v, Context c) {

        final Context context = c;

        try {

            final WeatherClient client = (new WeatherClient.ClientBuilder().attach(getActivity()))
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getForecastWeather(new WeatherRequest(LONGITUDE, LATITUDE), new WeatherClient.ForecastWeatherEventListener() {
                @Override
                public void onWeatherRetrieved(WeatherForecast forecast) {
                    List<DayForecast> dayForecastList = forecast.getForecast();
                    for (DayForecast dForecast : dayForecastList) {
                        Weather weather = dForecast.weather;
                        long timestamp = dForecast.timestamp;
                    }
                    List<String> arrayList = new ArrayList<String>();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyy");
                    for (int k = 0; k < 7; k++) {
                        Date date = c.getTime();
                        String formattedDate = df.format(date);
                        arrayList.add("Day: " + formattedDate + " - Temp: " + String.valueOf(dayForecastList.get(k).forecastTemp.day) + "°C");
                        c.add(Calendar.DATE, 1);
                    }
                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context, R.layout.list_item, R.id.listViewWeek, arrayList);
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {
                    wle.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

}
