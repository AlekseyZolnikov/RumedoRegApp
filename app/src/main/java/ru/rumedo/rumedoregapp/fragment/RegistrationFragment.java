package ru.rumedo.rumedoregapp.fragment;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;

import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.User;

import static android.content.Context.MODE_PRIVATE;

public class RegistrationFragment extends Fragment {

    private static final String KEY_REG_EVENT_FIELD = "KEY_ADMIN";
    private EditText regEventField;
    private EditText regNameField;
    private EditText regSurnameField;
    private EditText regEmailField;
    private EditText regPhoneField;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        sharedPref = getActivity().getPreferences(MODE_PRIVATE);

        Button regButton = view.findViewById(R.id.save_preference);
        regEventField = view.findViewById(R.id.reg_event_field);
        regNameField = view.findViewById(R.id.reg_name_field);
        regSurnameField = view.findViewById(R.id.reg_surname_field);
        regEmailField = view.findViewById(R.id.reg_email_field);
        regPhoneField = view.findViewById(R.id.reg_phone_field);

        loadPreferences(sharedPref);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(sharedPref);

                String event = regEventField.getText().toString();
                String name = regNameField.getText().toString();
                String surname = regSurnameField.getText().toString();
                String email = regEmailField.getText().toString();
                String phone = regPhoneField.getText().toString();

                User user = new User(name,surname,email,phone,event);

                Snackbar.make(getView(), "Update Success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    private void savePreferences(SharedPreferences sharedPref){
        SharedPreferences.Editor editor = sharedPref.edit();
        String value = regEventField.getText().toString();
        editor.putString(KEY_REG_EVENT_FIELD, value);
        editor.apply();
    }

    private void loadPreferences(SharedPreferences sharedPref){
        String adminName = sharedPref.getString(KEY_REG_EVENT_FIELD, "");
        regEventField.setText(adminName);
    }

}
