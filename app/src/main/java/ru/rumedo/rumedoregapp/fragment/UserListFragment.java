package ru.rumedo.rumedoregapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.rumedo.rumedoregapp.Apapter.OnRecyclerViewClickListener;
import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.Apapter.UserAdapter;
import ru.rumedo.rumedoregapp.User;
import ru.rumedo.rumedoregapp.UserService;

public class UserListFragment extends Fragment {

    public View view;
    public ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        getActivity().startService(new Intent(getActivity(), UserService.class));
        initRetrofit();
        requestRetrofit();
        return view;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void requestRetrofit() {
        String skey = "rumedo_rest_api_key";
        apiService.listUsers(skey)
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                    if (response.body() != null) {
                        Log.d("Retrofit", "onResponse: " + response.body().getUsers()[0].getName());
                        initRecyclerView(response.body().getUsers());
                        ProgressBar progressBar = view.findViewById(R.id.recycler_user_progress);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Log.e("Retrofit", "request failed", throwable);
                }
            });
    }

    private void initRecyclerView(User[] users) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(users, mOnRecyclerViewClickListener);
        recyclerView.setAdapter(userAdapter);
    }

    OnRecyclerViewClickListener mOnRecyclerViewClickListener = new OnRecyclerViewClickListener() {
        @Override
        public void showSingleItemInFragment(User user) {

            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();
            String phone = user.getPhone();

            Fragment fragment = new UserFragment();

            Bundle bundle = new Bundle();
            bundle.putString("KEY_USER_NAME", name);
            bundle.putString("KEY_USER_SURNAME", surname);
            bundle.putString("KEY_USER_EMAIL", email);
            bundle.putString("KEY_USER_PHONE", phone);
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_frame_layout, fragment)
                    .commit();
        }
    };
}
