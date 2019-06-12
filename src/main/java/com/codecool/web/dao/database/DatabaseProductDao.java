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
    public Product setAvailability(int product_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT set_product_availability(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product_id);
            int returned_id = fetchGeneratedId(statement);
            connection.commit();
            return findById(returned_id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Product findById(int id) throws SQLException {
        String sql = "SELECT * from find_product_by_id(?)";
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
        String sql = "SELECT * from find_all_products()";
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
        String picture = resultSet.getString("picture");
        int price = resultSet.getInt("price");
        return new Product(id, name, availability, unit, picture, price);
    }
}
