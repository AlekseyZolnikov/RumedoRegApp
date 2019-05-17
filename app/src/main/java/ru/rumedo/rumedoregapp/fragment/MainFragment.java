package ru.rumedo.rumedoregapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import ru.rumedo.rumedoregapp.R;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    private static final String KEY_ADMIN = "KEY_ADMIN";
    private EditText adminEditText;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button savePrefs = view.findViewById(R.id.save_preference);
        adminEditText = view.findViewById(R.id.edit_user_administrator);

        sharedPref = getActivity().getPreferences(MODE_PRIVATE);

        loadPreferences(sharedPref);

        savePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(sharedPref);    // сохранить настройки
                Snackbar.make(getView(), "Update Success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        return view;
    }


    // сохраняем настройки
    private void savePreferences(SharedPreferences sharedPref){
        SharedPreferences.Editor editor = sharedPref.edit();
        String value = adminEditText.getText().toString();
        editor.putString(KEY_ADMIN, value);

        editor.apply();
    }

    private void loadPreferences(SharedPreferences sharedPref){

        // для получения настроек нет необходимости в Editor, получаем их прямо из SharedPreferences
        String adminName = sharedPref.getString(KEY_ADMIN, "Не указан");

        adminEditText.setText(adminName);
    }


}
