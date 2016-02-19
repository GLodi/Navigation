package com.giuliolodi.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentWeatherForecast extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        final Context context = getActivity().getBaseContext();

        final ListView list = (ListView) v.findViewById(R.id.listItem);

        // Get coordinates from MainActivity class
        MainActivity mainActivity = (MainActivity) getActivity();
        Double latitude = Double.valueOf(mainActivity.LATITUDE);
        Double longitude = Double.valueOf(mainActivity.LONGITUDE);

        // Set Adapter


        // Initializing Weather Client Builder
        try {

            final WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getForecastWeather(new WeatherRequest(longitude, latitude), new WeatherClient.ForecastWeatherEventListener() {
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
                    list.setAdapter(myAdapter);
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

        } catch (Throwable t){
            t.printStackTrace();
        }

        return v;
    }

}
