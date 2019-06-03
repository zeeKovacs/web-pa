package com.codecool.web.dao.database;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.model.CartItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCartItemDao implements CartItemDao {

    @Override
    public CartItem findCartItemByCartId(int id) throws SQLException {
        return null;
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
