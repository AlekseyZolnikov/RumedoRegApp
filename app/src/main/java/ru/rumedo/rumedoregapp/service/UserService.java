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

    /**
     * Имитация метода возвращающего список пользователей
     * @return
     */
    private ArrayList<User> getUserList() {

        ArrayList<User> list = new ArrayList<>();

        list.add(new User(
                "Ivan",
                "Abramov",
                "Ivanovich",
                "dog@dog.bark",
                "+79756745643",
                "Moscow",
                45));

        return list;
    }

}
