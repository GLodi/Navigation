package com.android4dev.navigationview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.forecastio.ForecastIOProviderType;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.ArrayList;
import java.util.List;

public class FragmentWeatherForecast extends Fragment {

    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet",
            "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat", "placerat", "ante",
            "porttitor", "sodales", "pellentesque", "augue", "purus"};



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        final Context context = getActivity().getBaseContext();

        final TextView temp = (TextView) v.findViewById(R.id.temperatureTextF);
        final TextView condition = (TextView) v.findViewById(R.id.conditionTextF);
        final ListView list = (ListView) v.findViewById(R.id.list_item);

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
                    for (int k = 0; k < 2; k++) {
                        arrayList.add(String.valueOf(dayForecastList.get(k)));
                    }

                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context, R.layout.list_item, arrayList);
                    list.setAdapter(myAdapter);
                    condition.setText(String.valueOf(dayForecastList.get(0)));
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {
                    condition.setText("Weather error - parsing data");
                    wle.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable t) {
                    condition.setText("Communication error");
                    t.printStackTrace();
                }
            });

        } catch (Throwable t){
            t.printStackTrace();
        }

        return v;
    }

}
