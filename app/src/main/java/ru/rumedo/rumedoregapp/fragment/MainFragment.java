package ru.rumedo.rumedoregapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.UserService;

public class MainFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);


        Intent intent = new Intent(view.getContext(), UserService.class);
        getActivity().startService(intent);



        return view;
    }





}
