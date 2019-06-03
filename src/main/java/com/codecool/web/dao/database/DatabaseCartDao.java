package com.codecool.web.dao.database;

import com.codecool.web.dao.CartDao;
import com.codecool.web.model.Cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCartDao implements CartDao {

    @Override
    public Cart findCartByUserId(int id) throws SQLException {
        return null;
    }

    private Cart fetchCart(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        int price = resultSet.getInt("price");
        return new Cart(id, user_id, price);
    }
}
