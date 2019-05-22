package ru.rumedo.rumedoregapp.fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.rumedo.rumedoregapp.User;

public class ApiRequest {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("users")
    @Expose
    private User[] users;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
