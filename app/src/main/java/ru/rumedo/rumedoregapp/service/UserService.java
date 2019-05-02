package ru.rumedo.rumedoregapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ru.rumedo.rumedoregapp.entity.User;

public class UserService extends IntentService {

    public UserService() {
        super("UserService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getUserList();
    }

    private ArrayList<User> getUserList() {
        return new ArrayList<>();
    }


}
