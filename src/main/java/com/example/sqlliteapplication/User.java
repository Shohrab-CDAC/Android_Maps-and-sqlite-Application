package com.example.sqlliteapplication;// Filename: User.java

public class User {
    private int id;
    private String email;
    private String password;

    public User() {
        // Default constructor
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter and setter methods for id, email, and password
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
