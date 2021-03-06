package com.giuliolodi.navigation;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public String LATITUDE = "45.464283";
    public String LONGITUDE = "9.190263";

    public void setCoordinates(Double latitude, Double longitude){
        LATITUDE = String.valueOf(latitude);
        LONGITUDE = String.valueOf(longitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                // Closing drawer on item click
                drawerLayout.closeDrawers();

                // Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    // Replacing the main content with FragmentWeather which is our Inbox View;
                    case R.id.weather:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentWeather fragmentWeather = new FragmentWeather();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame, fragmentWeather);
                                fragmentTransaction.commit();
                                toolbar.setTitle("Weather");
                            }
                        }, 250);
                        return true;

                    case R.id.card:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentCard fragmentCard = new FragmentCard();
                                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction1.replace(R.id.frame, fragmentCard);
                                fragmentTransaction1.commit();
                                toolbar.setTitle("Card");
                            }
                        }, 250);
                        return true;

                    case R.id.location:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentLocation fragmentLocation = new FragmentLocation();
                                android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction2.replace(R.id.frame, fragmentLocation);
                                fragmentTransaction2.commit();
                                toolbar.setTitle("Location");
                            }
                        }, 250);
                        return true;

                    case R.id.forecast:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentWeatherForecast fragmentWeatherForecast = new FragmentWeatherForecast();
                                android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction3.replace(R.id.frame, fragmentWeatherForecast);
                                fragmentTransaction3.commit();
                                toolbar.setTitle("Forecast");
                            }
                        }, 250);
                        return true;

                    case R.id.request:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentRequest fragmentRequest = new FragmentRequest();
                                android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction4.replace(R.id.frame, fragmentRequest);
                                fragmentTransaction4.commit();
                                toolbar.setTitle("Request");
                            }
                        }, 250);
                        return true;
                    case R.id.notification:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentNotification fragmentNotification = new FragmentNotification();
                                android.support.v4.app.FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction5.replace(R.id.frame, fragmentNotification);
                                fragmentTransaction5.commit();
                                toolbar.setTitle("Notification");
                            }
                        }, 250);
                        return true;
                    case R.id.spam:
                        toolbar.setTitle("Spams");
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        // Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
