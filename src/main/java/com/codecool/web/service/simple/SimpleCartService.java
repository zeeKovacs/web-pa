package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.model.Cart;
import com.codecool.web.service.CartService;

import java.sql.SQLException;

public class SimpleCartService implements CartService {

    private final CartDao cartDao;

    public SimpleCartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }


    @Override
    public Cart findById(int id) throws SQLException {
        return cartDao.findById(id);
    }

    @Override
    public Cart createGuestCart() throws SQLException {
        return cartDao.createGuestCart();
    }

    @Override
    public Cart assignCartToUser(int cart_id, int user_id) throws SQLException {
        return cartDao.assignCartToUser(cart_id, user_id);
    }

    @Override
    public Cart createCart(int user_id) throws SQLException {
        return cartDao.createCart(user_id);
    }

    @Override
    public Cart findCartByUserId(int id) throws SQLException {
        return cartDao.findCartByUserId(id);
    }
}
