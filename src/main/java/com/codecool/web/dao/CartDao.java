package com.codecool.web.dao;

import com.codecool.web.model.Cart;

import java.sql.SQLException;

public interface CartDao {

    Cart findById(int id) throws SQLException;

    Cart createGuestCart() throws SQLException;

    Cart createCart(int user_id) throws SQLException;

    Cart findCartByUserId(int id) throws SQLException;

    Cart assignCartToUser(int cart_id, int user_id) throws SQLException;
}
