package com.codecool.web.model;

public class Cart extends AbstractModel {

    private final int user_id;
    private final int price;

    public Cart(int id, int user_id, int price) {
        super(id);
        this.user_id = user_id;
        this.price = price;
    }

    public int getUserId() {
        return this.user_id;
    }
}
