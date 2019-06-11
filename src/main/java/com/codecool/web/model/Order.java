package com.codecool.web.model;

public class Order extends AbstractModel {

    private final int user_id;
    private final int cart_id;
    private final String name;
    private final String email;
    private final boolean confirmed;
    private final boolean complete;

    public Order(int id, int user_id, int cart_id, String name, String email, boolean confirmed, boolean complete) {
        super(id);
        this.user_id = user_id;
        this.cart_id = cart_id;
        this.name = name;
        this.email = email;
        this.confirmed = confirmed;
        this.complete = complete;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isComplete() {
        return complete;
    }
}
