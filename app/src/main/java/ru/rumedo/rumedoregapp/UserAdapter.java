package ru.rumedo.rumedoregapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private final ArrayList<User> itemUser;

    public UserAdapter(ArrayList<User> itemUser) {
        this.itemUser = itemUser;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return itemUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView;
        final TextView surnameView;
        final TextView emailView;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.user_item_name);
            surnameView = itemView.findViewById(R.id.user_item_surname);
            emailView = itemView.findViewById(R.id.user_item_email);
        }

        private void bind(int position) {
            nameView.setText(itemUser.get(position).getName());
            surnameView.setText(itemUser.get(position).getSurname());
            emailView.setText(itemUser.get(position).getEmail());
        }
    }
}
