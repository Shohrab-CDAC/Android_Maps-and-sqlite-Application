package com.example.sqlliteapplication;

public class UserModel {
    private String name;
    private String email;
    private String location;
    private String password;
    private String phoneNumber;

    public UserModel() {
        // Required empty public constructor for Firebase
    }

    public UserModel(String name, String email, String location, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public UserModel(int userId, String userName, String userEmail, String userLocation, String userPassword) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(int userId) {
    }
}
