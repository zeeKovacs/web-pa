package com.codecool.web.dao;

import com.codecool.web.model.CartItem;

import java.sql.SQLException;

public interface CartItemDao {

    CartItem findCartItemByCartId(int id) throws SQLException;
}
