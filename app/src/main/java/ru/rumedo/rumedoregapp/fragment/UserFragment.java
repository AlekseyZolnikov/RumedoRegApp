package ru.rumedo.rumedoregapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.rumedo.rumedoregapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    TextView textViewName;
    TextView textViewSurname;
    TextView textViewEmail;
    TextView textViewPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        if (getArguments() != null) {
            textViewName = view.findViewById(R.id.user_name_field);
            textViewSurname = view.findViewById(R.id.user_surname_field);
            textViewEmail = view.findViewById(R.id.user_email_field);
            textViewPhone = view.findViewById(R.id.user_phone_field);

            textViewName.setText(getArguments().getString("KEY_USER_NAME"));
            textViewSurname.setText(getArguments().getString("KEY_USER_SURNAME"));
            textViewEmail.setText(getArguments().getString("KEY_USER_EMAIL"));
            textViewPhone.setText(getArguments().getString("KEY_USER_PHONE"));
        }
        return view;
    }

}
