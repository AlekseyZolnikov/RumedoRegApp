package ru.rumedo.rumedoregapp;

import java.util.Calendar;
import java.util.Date;

public class User {
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final String event;
    private final Date regdate;


    public User(String name, String surname, String email, String phone, String event) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.event = event;
        this.regdate = Calendar.getInstance().getTime();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getRegdate() {
        return regdate.toString();
    }

    public String getPhone() {
        return phone;
    }

    public String getEvent() {
        return event;
    }
}
