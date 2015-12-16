package com.giuliolodi.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

public class FragmentWeather extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_weather_card, container, false);

        final TextView temp = (TextView) v.findViewById(R.id.temperatureText);
        final TextView condition = (TextView) v.findViewById(R.id.conditionText);
        final TextView city = (TextView) v.findViewById(R.id.cityNameText);
        final ImageView image = (ImageView) v.findViewById(R.id.conditionImage);

        // Get coordinates from super class
        MainActivity mainActivity = (MainActivity) getActivity();
        Double latitude = Double.valueOf(mainActivity.LATITUDE);
        Double longitude = Double.valueOf(mainActivity.LONGITUDE);

        // Initializing Weather Client Builder
        try {

            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getCurrentCondition(new WeatherRequest(longitude, latitude), new WeatherClient.WeatherEventListener(){

                @Override
                public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    float currentHumidity = currentWeather.weather.currentCondition.getHumidity();
                    String conditionString = currentWeather.weather.currentCondition.getCondition();
                    city.setText(currentWeather.weather.location.getCity());
                    temp.setText(Float.toString(currentTemp) + "°C " + Float.toString(currentHumidity) + "%");
                    condition.setText(conditionString);

                    if (conditionString.equals("Rain")) {
                        image.setImageResource(R.drawable.weather_pouring);
                    }
                    else if (conditionString.equals("Clouds")) {
                        image.setImageResource(R.drawable.ic_cloud_black_48dp);
                    }
                    else if (conditionString.equals("Clear") || conditionString.equals("Sunny")) {
                        image.setImageResource(R.drawable.ic_wb_sunny_black_48dp);
                    }
                    else if (conditionString.equals("Snow")) {
                        image.setImageResource(R.drawable.weather_snowy);
                    }
                    else if (conditionString.equals("Mist")) {
                        image.setImageResource(R.drawable.weather_fog);
                    }

                }

                @Override
                public void onWeatherError(WeatherLibException e) {
                    city.setText("Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable throwable) {
                    city.setText("Connection error");
                    throwable.printStackTrace();
                }
            });

        } catch (Throwable t){
            t.printStackTrace();
        }



        return v;
    }

}
