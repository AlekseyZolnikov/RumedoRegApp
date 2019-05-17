package ru.rumedo.rumedoregapp;

public class User {
    private final String name;
    private final String surname;
    private final String email;


    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    String getEmail() {
        return email;
    }

}
