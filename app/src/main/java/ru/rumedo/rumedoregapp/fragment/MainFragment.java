package ru.rumedo.rumedoregapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.UserService;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistrationFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_frame_layout,fragment)
                        .commit();
            }
        });

        ImageView imageView = view.findViewById(R.id.imageView);
        Picasso
                .get()
                .load("https://rumedo.ru/wp-content/themes/rumedo/images/logo2.png")
                .into(imageView);

        return view;
    }





}
