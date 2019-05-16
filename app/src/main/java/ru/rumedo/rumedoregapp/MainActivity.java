package ru.rumedo.rumedoregapp;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ru.rumedo.rumedoregapp.fragment.MainFragment;
import ru.rumedo.rumedoregapp.fragment.UserListFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivityTag";
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor temperature;
    private Sensor humidity;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        Bundle bundle = new Bundle();
        bundle.putString(MainFragment.KEY_BUNDLE_SENSORS, getSensors());
        mainFragment = new MainFragment();
        showFragment(mainFragment);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorHumidityListener, humidity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorTempListener, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorHumidityListener, humidity);
        sensorManager.unregisterListener(sensorTempListener, temperature);
    }

    private String getSensors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor sensor : sensors) {
            stringBuilder
                    .append("name = ").append(sensor.getStringType()).append("\n");
        }

        return stringBuilder.toString();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame_layout, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_main_add) {
            Log.d(TAG, "Add new user");
            return true;
        }else if (id == R.id.menu_main_statistic) {
            Log.d(TAG, "Show Full Statistic");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_list) {
            Fragment userListFragment = new UserListFragment();
            showFragment(userListFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showTemperatureSensors(SensorEvent event) {
        mainFragment.setTemperatureValue("Temp Sensor value = " + event.values[0] + "\n");
    }

    private void showHumiditySensors(SensorEvent event) {
        mainFragment.setHumidityValue("Humidity Sensor value = " + event.values[0] + "\n");

    }

    private final SensorEventListener sensorHumidityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensors(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private final SensorEventListener sensorTempListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensors(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
