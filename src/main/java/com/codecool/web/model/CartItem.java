package com.codecool.web.model;

public class CartItem extends AbstractModel {

    private final int cart_id;
    private final int product_id;
    private final int quantity;
    private final int price;

    public CartItem(int id, int cart_id, int product_id, int quantity, int price) {
        super(id);
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }
}
