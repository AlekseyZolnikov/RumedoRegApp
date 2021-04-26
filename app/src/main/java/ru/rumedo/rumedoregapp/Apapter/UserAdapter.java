package ru.rumedo.rumedoregapp.Apapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.rumedo.rumedoregapp.R;
import ru.rumedo.rumedoregapp.pojo.User;
import ru.rumedo.rumedoregapp.database.UserDataReader;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private UserDataReader userDataReader;

    public UserAdapter(UserDataReader userDataReader) {
        this.userDataReader = userDataReader;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(userDataReader.getPosition(i));
    }

    @Override
    public int getItemCount() {
        return userDataReader.getCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView;
        final TextView surnameView;
        final TextView emailView;
        final TextView regdateView;
        final TextView isSync;
        View userItem;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.user_item_name);
            surnameView = itemView.findViewById(R.id.user_item_surname);
            emailView = itemView.findViewById(R.id.user_item_email);
            regdateView = itemView.findViewById(R.id.user_item_regdate);
            isSync = itemView.findViewById(R.id.user_item_sync);
            userItem = itemView.findViewById(R.id.user_item);
        }

        private void bind(User user) {
            nameView.setText(user.getName());
            surnameView.setText(user.getSurname());
            emailView.setText(user.getEmail());
            regdateView.setText(user.getRegdate());
            isSync.setText(user.getIsSync() + "");
            if (user.getIsSync() != 1) {
                userItem.setBackgroundColor(Color.RED);
            }else {
                userItem.setBackgroundColor(Color.WHITE);
            }
        }
    }
}
