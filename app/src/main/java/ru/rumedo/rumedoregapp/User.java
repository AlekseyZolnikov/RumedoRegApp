package ru.rumedo.rumedoregapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

public class User {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private final String name;
    @SerializedName("surname")
    @Expose
    private final String surname;
    @SerializedName("email")
    @Expose
    private final String email;
    @SerializedName("phone")
    @Expose
    private final String phone;
    @SerializedName("event")
    @Expose
    private final String event;
    @SerializedName("regdate")
    @Expose
    private String regdate;


    public User(String name, String surname, String email, String phone, String event) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.event = event;
    }

    public String getId() {
        return id;
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
        return regdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getEvent() {
        return event;
    }
}
