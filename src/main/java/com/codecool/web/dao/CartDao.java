package com.codecool.web.dao;

import com.codecool.web.model.Cart;

import java.sql.SQLException;

public interface CartDao {

    Cart findCartByUserId(int id) throws SQLException;
}
