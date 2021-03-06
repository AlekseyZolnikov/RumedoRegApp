package ru.rumedo.rumedoregapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.pojo.User;
import ru.rumedo.rumedoregapp.database.UserDataSource;

import static android.content.Context.MODE_PRIVATE;

public class RegistrationFragment extends Fragment {

    private static final String KEY_REG_EVENT_FIELD = "KEY_ADMIN";
    private EditText regEventField;
    private EditText regNameField;
    private EditText regSurnameField;
    private EditText regEmailField;
    private EditText regPhoneField;
    private AutoCompleteTextView regCityField;
    private CheckBox regIsUpdate;
    private Button regButton;
    private ProgressBar regProgress;
    private SharedPreferences sharedPref;
    private ApiService apiService;
    private UserDataSource userDataSource;
    public View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        initDataSource();
        initRetrofit();
        initGui(view);
        initPreference();
        initEvents(view);

        return view;
    }

    private void initDataSource() {
        userDataSource = new UserDataSource(getContext());
        userDataSource.open();
    }

    private void initPreference() {
        sharedPref = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
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
                String city = regCityField.getText().toString();
                String isUpdate = Boolean.toString(regIsUpdate.isChecked());

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(getView(), "Email is empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    returnStateBtn();
                    return;
                }

                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setEmail(email);
                user.setPhone(phone);
                user.setEvent(event);
                user.setCity(city);
                user.setIsSync(1);

                regProgress.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getActivity().getCurrentFocus();

                sendUserData(user, isUpdate);
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
        regIsUpdate = view.findViewById(R.id.reg_is_update);
        regCityField = view.findViewById(R.id.reg_city_field);

        String[] cities = getResources().getStringArray(R.array.cities);
        List<String> cityList = Arrays.asList(cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_dropdown_item_1line, cityList);
        regCityField.setAdapter(adapter);

    }

    private void initRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/v2/")
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

    private void sendUserData(final User user, String isUpdate) {
        String skey = "rumedo_rest_api_key";
        apiService.addUser(skey, user.getName(),user.getSurname(),user.getEmail(),user.getPhone(), user.getEvent(), user.getCity()/*isUpdate*/)
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                    if (response.body() != null) {
                        Snackbar.make(getView(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        if (!response.body().getStatus().equals("rejected")) {
                            userDataSource.addUser(user);
                            clearEditText();
                        }
                        regProgress.setVisibility(View.INVISIBLE);
                        returnStateBtn();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Snackbar.make(view, "Request field! Save in local storage", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    user.setIsSync(0);
                    userDataSource.addUser(user);
                    regProgress.setVisibility(View.INVISIBLE);
                    returnStateBtn();
                    clearEditText();
                }



            });
    }

    private void returnStateBtn() {
        regButton.setClickable(true);
        regButton.setBackgroundResource(R.color.colorPrimary);
    }

    private void clearEditText() {

        regNameField.setText("");
        regSurnameField.setText("");
        regEmailField.setText("");
        regPhoneField.setText("");
        regIsUpdate.setChecked(false);
    }
}
