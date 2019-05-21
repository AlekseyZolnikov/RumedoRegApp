package ru.rumedo.rumedoregapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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
    private Button regButton;
    private ProgressBar regProgress;
    private SharedPreferences sharedPref;
    private ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initRetrofit();
        initGui(view);
        initPreference();
        initEvents(view);

        return view;
    }

    private void initPreference() {
        sharedPref = getActivity().getPreferences(MODE_PRIVATE);
        loadPreferences(sharedPref);
    }

    private void initEvents(View view) {
        regButton = view.findViewById(R.id.save_preference);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(sharedPref);

                regButton.setClickable(false);
                regButton.setBackgroundResource(R.color.colorDisabled);

                String event = regEventField.getText().toString();
                String name = regNameField.getText().toString();
                String surname = regSurnameField.getText().toString();
                String email = regEmailField.getText().toString();
                String phone = regPhoneField.getText().toString();

                User user = new User(name,surname,email,phone,event);

                regProgress.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getActivity().getCurrentFocus();

                requestRetrofit(user);
            }
        });
    }

    private void initGui(View view) {
        regEventField = view.findViewById(R.id.reg_event_field);
        regNameField = view.findViewById(R.id.reg_name_field);
        regSurnameField = view.findViewById(R.id.reg_surname_field);
        regEmailField = view.findViewById(R.id.reg_email_field);
        regPhoneField = view.findViewById(R.id.reg_phone_field);
        regProgress = view.findViewById(R.id.reg_progress);
    }

    private void initRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
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
        String skey = "rumedo_rest_api_key";
        apiService.addUser(skey, user.getName(),user.getSurname(),user.getEmail(),user.getPhone(), user.getEvent())
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                    if (response.body() != null) {
                        Snackbar.make(getView(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Snackbar.make(getView(), "API response is wrong", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    regProgress.setVisibility(View.INVISIBLE);
                    returnStateBtn();
                    clearEditText();
                }

                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Snackbar.make(getView(), "request failed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.e("Retrofit", "request failed", throwable);

                    regProgress.setVisibility(View.INVISIBLE);
                    returnStateBtn();
                }

                private void returnStateBtn() {

                    regButton.setClickable(true);
                    regButton.setBackgroundResource(R.color.colorPrimary);
                }

            });
    }

    private void clearEditText() {
        regNameField.setText("");
        regSurnameField.setText("");
        regEmailField.setText("");
        regPhoneField.setText("");
    }
}
