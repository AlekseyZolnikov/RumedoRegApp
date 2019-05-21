package ru.rumedo.rumedoregapp.Apapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private final User[] itemUser;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;

    public UserAdapter(User[] itemUser, OnRecyclerViewClickListener mOnRecyclerViewClickListener) {
        this.itemUser = itemUser;
        this.mOnRecyclerViewClickListener = mOnRecyclerViewClickListener;
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
        return itemUser.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView;
        final TextView surnameView;
        final TextView emailView;
        final TextView regdateView;
        View userItem;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.user_item_name);
            surnameView = itemView.findViewById(R.id.user_item_surname);
            emailView = itemView.findViewById(R.id.user_item_email);
            regdateView = itemView.findViewById(R.id.user_item_regdate);
            userItem = itemView.findViewById(R.id.user_item);

            userItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mOnRecyclerViewClickListener.showSingleItemInFragment(itemUser[pos]);
                }
            });

        }

        private void bind(int pos) {
            nameView.setText(itemUser[pos].getName());
            surnameView.setText(itemUser[pos].getSurname());
            emailView.setText(itemUser[pos].getEmail());
            regdateView.setText(itemUser[pos].getRegdate());
        }
    }
}
