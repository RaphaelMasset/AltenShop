package com.example.altenshop_backend;

public class User {
    private long id;
    private String username;
    private String firstname;
    private String email;
    private String password; // Stockée hashée (à ne jamais stocker en clair)

    public User() {
    }

    public User(long id, String username, String firstname, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }

    // Getters et setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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
