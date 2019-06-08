package com.codecool.web.dao;

import com.codecool.web.model.CartItem;

import java.sql.SQLException;
import java.util.List;

public interface CartItemDao {

    CartItem findById(int id) throws SQLException;

    void removeItemFromCart(int item_id) throws SQLException;

    List<CartItem> findCartItemsByCartId(int id) throws SQLException;

    CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException;
}
