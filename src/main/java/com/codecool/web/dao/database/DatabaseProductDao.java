package com.codecool.web.dao.database;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseProductDao implements ProductDao {

    @Override
    public Product findProductById(int id) throws SQLException {
        return null;
    }

    private Product fetchProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        boolean availability = resultSet.getBoolean("availability");
        String unit = resultSet.getString("unit");
        int price = resultSet.getInt("price");
        return new Product(id, name, availability, unit, price);
    }
}
