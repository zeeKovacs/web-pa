package com.codecool.web.service;

import com.codecool.web.model.CartItem;

import java.sql.SQLException;
import java.util.List;

public interface CartItemService {

    List<CartItem> findCartItemsByCartId(int cart_id) throws SQLException;

    CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException;
}
