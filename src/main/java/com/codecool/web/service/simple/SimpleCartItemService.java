package com.codecool.web.service.simple;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.model.CartItem;
import com.codecool.web.service.CartItemService;

import java.sql.SQLException;
import java.util.List;

public class SimpleCartItemService implements CartItemService {

    private final CartItemDao cartItemDao;

    public SimpleCartItemService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    @Override
    public CartItem addToCart(int cart_id, int product_id, int quantity) throws SQLException {
        return cartItemDao.addToCart(cart_id, product_id, quantity);
    }

    @Override
    public void removeItemFromCart(int item_id) throws SQLException {
        cartItemDao.removeItemFromCart(item_id);
    }

    @Override
    public List<CartItem> findCartItemsByCartId(int cart_id) throws SQLException {
        return cartItemDao.findCartItemsByCartId(cart_id);
    }
}
