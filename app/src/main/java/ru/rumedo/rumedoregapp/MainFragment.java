package ru.rumedo.rumedoregapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public static final String KEY_BUNDLE_SENSORS = "KEY_BUNDLE_SENSORS";
    private TextView tempSensor;
    private TextView humiditySensor;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tempSensor = view.findViewById(R.id.temp_sensor);
        humiditySensor = view.findViewById(R.id.humidity_sensor);

        return view;
    }

    public void setTemperatureValue(String value) {
        tempSensor.setText(value);
    }

    public void setHumidityValue(String value) {
        humiditySensor.setText(value);
    }



}
