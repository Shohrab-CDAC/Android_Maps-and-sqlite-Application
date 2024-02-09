package com.example.sqlliteapplication;

// UserModel2.java
public class UserModel2 {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    public UserModel2() {
        // Default constructor required for calls to DataSnapshot.getValue(UserModel2.class)
    }

    public UserModel2(String name, String phoneNumber, String email, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Add a method to hide the password
    public String getHiddenPassword() {
        // Return a hidden version of the password (e.g., asterisks)
        return "*".repeat(password.length());
    }
}
