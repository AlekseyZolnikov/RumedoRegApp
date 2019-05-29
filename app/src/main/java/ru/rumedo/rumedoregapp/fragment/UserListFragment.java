package ru.rumedo.rumedoregapp.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import ru.rumedo.rumedoregapp.database.UserDataReader;
import ru.rumedo.rumedoregapp.database.UserDataSource;

public class UserListFragment extends Fragment {

    public View view;
    private UserDataSource userDataSource;
    private UserDataReader userDataReader;
    private UserAdapter adapter;
    private FloatingActionButton fab;
    public ApiService apiService;
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        initGui();
        initEvents();
        initRetrofit();
        requestRetrofit();
        return view;
    }


    private void initGui() {
        fab = view.findViewById(R.id.btn_sync);
    }

    private void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initDataSource() {
        userDataSource = new UserDataSource(getContext());
        userDataSource.open();
        userDataReader = userDataSource.getUserDataReader();
    }

    private void initRecyclerView(User[] users) {
        recyclerView = view.findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);

    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void requestRetrofit() {
        final ProgressBar progressBar = view.findViewById(R.id.recycler_user_progress);
        String skey = "rumedo_rest_api_key";
        apiService.listUsers(skey)
                .enqueue(new Callback<ApiRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                        if (response.body() != null) {
                            Log.d("Retrofit", "onResponse: " + response.body().getUsers()[0].getName());
                            initRecyclerView(response.body().getUsers());

                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar.make(getView(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ApiRequest> call,
                                          @NonNull Throwable throwable) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(getView(), "Connection refused", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
    }


}
