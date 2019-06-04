package com.codecool.web.dao.database;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProductDao extends AbstractDao implements ProductDao {

    public DatabaseProductDao(Connection connection) {
        super(connection);
    }

    @Override
    public Product findProductById(int id) throws SQLException {
        String sql = "SELECT * from find_cart_by_user_id(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchProduct(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * from find_all_user()";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(fetchProduct(resultSet));
            }
            return products;
        }
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
