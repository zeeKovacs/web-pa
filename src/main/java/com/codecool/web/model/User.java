package com.codecool.web.model;

public final class User extends AbstractModel {

    private final String name;
    private final String email;
    private final Role role;
    private final String password;
    private final String phone_number;

    public User(int id, String name, Role role, String email, String password, String phone_number) {
        super(id);
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.phone_number = phone_number;
    }

    public enum Role {
        GUEST, USER, ADMIN
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
