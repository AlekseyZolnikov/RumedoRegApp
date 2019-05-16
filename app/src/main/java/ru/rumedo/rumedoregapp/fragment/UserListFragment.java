package ru.rumedo.rumedoregapp.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.UserAdapter;
import ru.rumedo.rumedoregapp.User;
import ru.rumedo.rumedoregapp.UserService;

public class UserListFragment extends Fragment {

    private ArrayList<User> itemUserArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        getActivity().startService(new Intent(getActivity(), UserService.class));

        CreateUserList userTask = new CreateUserList();
        userTask.execute();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_user_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(itemUserArrayList);
        recyclerView.setAdapter(userAdapter);
    }

    class CreateUserList extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            itemUserArrayList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 1; i <= 2000000; i++) {

                itemUserArrayList.add(new User(
                        "Пользователь " + i,
                        "с фамилией № " + i,
                        "dog" + i + "@bark.uw"
                ));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            initRecyclerView();
            ProgressBar progressBar = getView().findViewById(R.id.recycler_user_progress);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}
