package com.iveej.inventorysystem;

public class User {
    private final String username;
    private final String firstName;
    private final String role;

    User(String username, String firstName, String role) {
        this.username = username;
        this.firstName = firstName;
        this.role = role;

        System.out.println("User created.");
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getRole() {
        return this.role;
    }
}
