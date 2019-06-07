package com.codecool.web.model;

public class CartItem extends AbstractModel {

    private final int quantity;
    private final int price;
    private final String unit;
    private final String picture;
    private final String name;

    public CartItem(int id, String name, String unit, String picture, int quantity, int price) {
        super(id);
        this.name = name;
        this.unit = unit;
        this.picture = picture;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }
}
