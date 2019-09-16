package ru.rumedo.rumedoregapp.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import ru.rumedo.rumedoregapp.database.DatabaseHelper;
import ru.rumedo.rumedoregapp.database.UserDataReader;
import ru.rumedo.rumedoregapp.database.UserDataSource;

public class UserListFragment extends Fragment {

    private static String ACTION = "ru.rumedo.rumedoregapp.fragment.UserListFragment";

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
        initDataSource();
        initRecyclerView(userDataReader);
//        initRetrofit();
//        requestRetrofit();
        return view;
    }


    private void initGui() {
        fab = view.findViewById(R.id.btn_sync);
    }

    private void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRetrofit();

                for (int i = 0; i < userDataReader.getCount(); i++) {
                    if(userDataReader.getPosition(i).getIsSync() != 1) {
                        requestRetrofit(userDataReader.getPosition(i));

                    }
                }

                Snackbar.make(view, "Sync Request Send", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initDataSource() {
//        getContext().deleteDatabase("users.db");
        userDataSource = new UserDataSource(getContext());
        userDataSource.open();
        userDataReader = userDataSource.getUserDataReader();
    }

    private void initRecyclerView(UserDataReader userDataReader) {
        recyclerView = view.findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserAdapter(userDataReader);
        recyclerView.setAdapter(adapter);

    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void requestRetrofit(final User user) {
        String skey = "rumedo_rest_api_key";
        apiService.addUser(skey, user.getName(),user.getSurname(),user.getEmail(),user.getPhone(), user.getEvent(), "0")
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                    if (response.body() != null) {
                        if (!response.body().getStatus().equals("rejected")) {
                            long updateId = userDataSource.updateUser(user);
                            Log.d("SYNC", "onResponse: " + updateId);
                            Snackbar.make(view, "Sync Success", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Snackbar.make(view, "Sync field!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
    }



}
