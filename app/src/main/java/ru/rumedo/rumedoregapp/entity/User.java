package ru.rumedo.rumedoregapp.entity;

public class User {
    private String name;
    private String surname;
    private String lastname;
    private String email;
    private String phone;
    private String city;
    private int age;

    public User(String name, String surname, String lastname, String email, String phone, String city, int age) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
