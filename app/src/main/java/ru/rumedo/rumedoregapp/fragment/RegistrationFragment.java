package ru.rumedo.rumedoregapp.fragment;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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
    private APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        initRetrofit();

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

    private void initRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") // Базовая часть адреса
                // Конвертер, необходимый для преобразования JSON'а в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Создаем объект, при помощи которого будем выполнять запросы
        apiService = retrofit.create(APIService.class);
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

    private void requestRetrofit(User user) {
        apiService.load(city, keyApi)
                .enqueue(new Callback<RumedoRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<RumedoRequest> call,
                                           @NonNull Response<RumedoRequest> response) {
                        if (response.body() != null) {

                        }
//                            textTemp.setText(Float.toString(response.body().getMain().getTemp()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<RumedoRequest> call,
                                          @NonNull Throwable throwable) {
                        Log.e("Retrofit", "request failed", throwable);
//                        textTemp.setText(R.string.error);
                    }
                });
    }

}
