package com.giuliolodi.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;

public class FragmentRequest extends Fragment {

    public String requestString;
    public String cityRequested;
    public TextView requestCity;
    public EditText editText;

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
                    getCitySearch(v);
                    getWeather(v);
                    return true;
                }
                return false;
            }
        });
        return v;

    }

    public void getCitySearch(View v) {

        requestCity = (TextView) v.findViewById(R.id.request_city);
        editText = (EditText) v.findViewById(R.id.edit_text);

        try {

            final WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.searchCity(requestString, new WeatherClient.CityEventListener() {
                @Override
                public void onCityListRetrieved(List<City> cityList) {
                    cityRequested = String.valueOf(cityList.get(0).getId());
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {

                }

                @Override
                public void onConnectionError(Throwable t) {

                }
            });

        }
        catch(Throwable t) {
            t.printStackTrace();
        }

    }

    public void getWeather(View v) {

        requestCity = (TextView) v.findViewById(R.id.request_city);
        editText = (EditText) v.findViewById(R.id.edit_text);

        // Initializing Weather Client Builder
        try {

            final WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getCurrentCondition(new WeatherRequest(cityRequested), new WeatherClient.WeatherEventListener() {
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

}
