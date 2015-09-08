package com.android4dev.navigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.forecastio.ForecastIOProviderType;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

public class FragmentWeather extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        final TextView temp = (TextView) v.findViewById(R.id.currentTemp);
        final TextView condition = (TextView) v.findViewById(R.id.currentCondition);

        // Initializing Weather Client Builder
        try {

            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getCurrentCondition(new WeatherRequest("3173435"), new WeatherClient.WeatherEventListener(){

                @Override
                public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    String conditionString = currentWeather.weather.currentCondition.getCondition();
                    temp.setText("City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");
                    condition.setText(conditionString);
                }

                @Override
                public void onWeatherError(WeatherLibException e) {
                    temp.setText("Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable throwable) {
                    temp.setText("Connection error");
                    throwable.printStackTrace();
                }
            });

        } catch (Throwable t){
            t.printStackTrace();
        }
        return v;
    }

}
