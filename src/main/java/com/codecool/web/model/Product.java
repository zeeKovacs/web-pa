package com.codecool.web.model;

public class Product extends AbstractModel {

    private final String name;
    private final boolean availability;
    private final String unit;
    private final int price;

    public Product(int id, String name, boolean availability, String unit, int price) {
        super(id);
        this.name = name;
        this.availability = availability;
        this.unit = unit;
        this.price = price;
    }
}
