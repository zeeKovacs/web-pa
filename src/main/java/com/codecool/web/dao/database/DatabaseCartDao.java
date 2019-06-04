package com.codecool.web.dao.database;

import com.codecool.web.dao.CartDao;
import com.codecool.web.model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCartDao extends AbstractDao implements CartDao {

    public DatabaseCartDao(Connection connection) {
        super(connection);
    }

    @Override
    public Cart findCartByUserId(int id) throws SQLException {
        String sql = "SELECT * from find_cart_by_user_id(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchCart(resultSet);
                }
            }
        }
        return null;
    }

    private Cart fetchCart(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        int price = resultSet.getInt("price");
        return new Cart(id, user_id, price);
    }
}
