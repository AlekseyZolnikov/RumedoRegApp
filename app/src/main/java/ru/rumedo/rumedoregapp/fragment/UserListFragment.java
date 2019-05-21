package ru.rumedo.rumedoregapp.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

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

    private ArrayList<User> itemUserArrayList;
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
                        CreateUserList userTask = new CreateUserList(response);
                        userTask.execute();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiRequest> call,
                                      @NonNull Throwable throwable) {
                    Log.e("Retrofit", "request failed", throwable);
                }
            });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(itemUserArrayList, mOnRecyclerViewClickListener);
        recyclerView.setAdapter(userAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    class CreateUserList extends AsyncTask<Void,Void,Void> {

        Response<ApiRequest> response;
        public CreateUserList(Response<ApiRequest> response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            itemUserArrayList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            User[] users = response.body().getUsers();
            for (User user: users
            ) {
                itemUserArrayList.add(new User(
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getEvent()
                ));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            initRecyclerView();
            ProgressBar progressBar = view.findViewById(R.id.recycler_user_progress);
            progressBar.setVisibility(View.INVISIBLE);
        }
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
