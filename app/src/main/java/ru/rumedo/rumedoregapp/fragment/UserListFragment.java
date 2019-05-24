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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.reflect.Array;

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
        InitTask task = new InitTask();
        task.execute();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class InitTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            initDataSource();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initRecyclerView();
            ProgressBar progress = getView().findViewById(R.id.recycler_user_progress);
            progress.setVisibility(View.INVISIBLE);
        }
    }

    private void initGui() {
        fab = view.findViewById(R.id.btn_sync);
    }

    private void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncUserList();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserAdapter(userDataReader);
        recyclerView.setAdapter(adapter);
    }

    private void initDataSource() {
        userDataSource = new UserDataSource(getContext());
        userDataSource.open();
        userDataReader = userDataSource.getUserDataReader();
    }

    private void syncUserList() {
        initRetrofit();
        getRemoteUsers();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rumedo.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void getRemoteUsers() {
        String skey = "rumedo_rest_api_key";
        apiService.listUsers(skey)
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {
                    if (response.body() != null) {

                        ProgressBar progressBar = view.findViewById(R.id.recycler_user_progress);
                        progressBar.setVisibility(View.INVISIBLE);

                        User[] users = response.body().getUsers();

                        trySyncUsers(users);

                        Snackbar.make(getView(), "list update", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Log.e("Retrofit", "request failed", throwable);
                    Snackbar.make(getView(), "Sync failed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
    }

    private void trySyncUsers(User[] users) {

        UserDataReader in = userDataSource.getUserDataReader();
        User[] out = users;

        // Обновляем локальные данные
        for (User user : out) {
            if (!isRemoteUserHasInUserData(user.getEmail(), in)) {
                userDataSource.addUser(user);
            }
        }

        // Обновляем удаленные
        for (int i = 0; i < in.getCount(); i++) {
            if (!isUserDataHasInRemote(in.getPosition(i).getEmail(), out)) {
                addUserInRemoteDatabase(in.getPosition(i));
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void addUserInRemoteDatabase(User user) {
        String skey = "rumedo_rest_api_key";
        apiService.addUser(skey, user.getName(),user.getSurname(),user.getEmail(),user.getPhone(), user.getEvent())
            .enqueue(new Callback<ApiRequest>() {
                @Override
                public void onResponse(@NonNull Call<ApiRequest> call, @NonNull Response<ApiRequest> response) {

                }

                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {

                }

            });

    }

    private boolean isUserDataHasInRemote(String email, User[] user) {
        for (int i = 0; i < user.length; i++) {
            if (user[i].getEmail().equals(email)) return true;
        }
        return false;
    }

    private boolean isRemoteUserHasInUserData(String email, UserDataReader remoteUser) {
        for (int i = 0; i < remoteUser.getCount(); i++) {
            if (remoteUser.getPosition(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    OnRecyclerViewClickListener mOnRecyclerViewClickListener = new OnRecyclerViewClickListener() {
        @Override
        public void showSingleItemInFragment(User user) {

            long id = user.getId();
            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();
            String phone = user.getPhone();

            Fragment fragment = new UserFragment();

            Bundle bundle = new Bundle();
            bundle.putLong( "KEY_USER_ID", id);
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
