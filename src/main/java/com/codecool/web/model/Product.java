package com.codecool.web.model;

public class Product extends AbstractModel {

    private final String name;
    private final boolean availability;
    private final String unit;
    private final String picture;
    private final int price;

    public Product(int id, String name, boolean availability, String unit, String picture, int price) {
        super(id);
        this.name = name;
        this.availability = availability;
        this.unit = unit;
        this.picture = picture;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getPicture() {
        return this.picture;
    }

    public int getPrice() {
        return this.price;
    }

    public String getUnit() {
        return this.unit;
    }

    public boolean getAvailability() {
        return availability;
    }
}
