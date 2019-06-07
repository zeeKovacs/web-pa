package com.codecool.web.dao.database;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCartItemDao extends AbstractDao implements CartItemDao {

    public DatabaseCartItemDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CartItem> findCartItemsByCartId(int id) throws SQLException {
        String sql = "SELECT * from find_items_by_cart_id(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<CartItem> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(fetchCartItem(resultSet));
                }
                return items;
            }
        }
    }

    @Override
    public CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT add_cart_item(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_id);
            statement.setInt(2, product_id);
            statement.setInt(3, quantity);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return findById(id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public CartItem findById(int id) throws SQLException {
        String sql = "SELECT * from find_cart_item_by_id(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchCartItem(resultSet);
                }
            }
        }
        return null;
    }

    private CartItem fetchCartItem(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int quantity = resultSet.getInt("quantity");
        String unit = resultSet.getString("unit");
        String name = resultSet.getString("name");
        String picture = resultSet.getString("picture");
        int price = resultSet.getInt("price");
        return new CartItem(id, name, unit, picture, quantity, price);
    }
}
