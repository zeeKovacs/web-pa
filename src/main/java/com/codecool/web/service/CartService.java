package com.codecool.web.service;

import com.codecool.web.model.Cart;

import java.sql.SQLException;

public interface CartService {

    Cart findById(int id) throws SQLException;

    Cart createGuestCart() throws SQLException;

    Cart createCart(int user_id) throws SQLException;

    Cart findCartByUserId(int id) throws SQLException;
}
