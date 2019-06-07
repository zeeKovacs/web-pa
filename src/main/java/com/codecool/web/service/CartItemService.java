package com.codecool.web.service;

import com.codecool.web.model.CartItem;

import java.sql.SQLException;

public interface CartItemService {

    CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException;
}
