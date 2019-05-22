package ru.rumedo.rumedoregapp.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import ru.rumedo.rumedoregapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    TextView textViewName;
    TextView textViewSurname;
    TextView textViewEmail;
    TextView textViewPhone;
    TextView textViewId;
    Button btnDelete;
    AlertDialog.Builder ad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initGui(view);
        initEvents(view);
        setDataIfIsNotNull();
        return view;
    }

    private void setDataIfIsNotNull() {
        if (getArguments() != null) {
            textViewId.setText(getArguments().getString("KEY_USER_ID"));
            textViewName.setText(getArguments().getString("KEY_USER_NAME"));
            textViewSurname.setText(getArguments().getString("KEY_USER_SURNAME"));
            textViewEmail.setText(getArguments().getString("KEY_USER_EMAIL"));
            textViewPhone.setText(getArguments().getString("KEY_USER_PHONE"));
        }
    }

    private void initEvents(View view) {
        ad.setTitle("Подтвердите действие");
        ad.setPositiveButton("Удалить", new OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        ad.setNegativeButton("Не удалять", new OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
    }

    private void initGui(View view) {
        ad = new AlertDialog.Builder(view.getContext());
        btnDelete = view.findViewById(R.id.user_btn_delete);
        textViewId = view.findViewById(R.id.user_id_field);
        textViewName = view.findViewById(R.id.user_name_field);
        textViewSurname = view.findViewById(R.id.user_surname_field);
        textViewEmail = view.findViewById(R.id.user_email_field);
        textViewPhone = view.findViewById(R.id.user_phone_field);
    }

}
