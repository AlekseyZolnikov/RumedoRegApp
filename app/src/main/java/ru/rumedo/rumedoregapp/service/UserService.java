package ru.rumedo.rumedoregapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class UserService extends IntentService {

    private static final String TAG = "UserService";

    public UserService() {
        super("UserService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getUserList();
    }

    private void getUserList() {
        Log.d(TAG, "getUserList");
    }


}
