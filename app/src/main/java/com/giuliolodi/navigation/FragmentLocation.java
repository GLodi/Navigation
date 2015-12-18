package com.giuliolodi.navigation;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

public class FragmentLocation extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        final TextView longText = (TextView) v.findViewById(R.id.longitudeTextView);
        final TextView latiText = (TextView) v.findViewById(R.id.latidudeTextView);

        // Initialize LocationManager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                longText.setText("Longitude: " + Double.toString(longitude));
                latiText.setText("Latitude: " + Double.toString(latitude));
                MainActivity mainActivity = (MainActivity) getActivity();

                // The reference to Main Activity becomes null when the user is not in FragmentLocation,
                // so in order to avoid a nullPointerException I need to check if the reference exists
                if (mainActivity != null) {
                    mainActivity.setCoordinates(latitude, longitude);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 100, locationListener);
        }
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2 * 60 * 1000, 100, locationListener);
        }
        return v;
    }
}
