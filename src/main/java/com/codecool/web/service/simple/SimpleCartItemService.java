package com.codecool.web.service.simple;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.model.CartItem;
import com.codecool.web.service.CartItemService;

import java.sql.SQLException;

public class SimpleCartItemService implements CartItemService {

    private final CartItemDao cartItemDao;

    public SimpleCartItemService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    @Override
    public CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException {
        return cartItemDao.addToCart(cart_id, product_id, quantity);
    }
}
