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
        String sql = "SELECT * from find_item_by_cart_id(?)";
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

    private CartItem fetchCartItem(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int cart_id = resultSet.getInt("cart_id");
        int product_id = resultSet.getInt("product_id");
        int quantity = resultSet.getInt("quantity");
        int price = resultSet.getInt("price");
        return new CartItem(id, cart_id, product_id, quantity, price);
    }
}
