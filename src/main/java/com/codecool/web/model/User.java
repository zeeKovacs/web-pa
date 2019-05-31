package com.codecool.web.model;

public final class User extends AbstractModel {

    private final String email;
    private final String password;

    public User(int id, String email, String password) {
        super(id);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
