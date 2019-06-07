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

    @Override
    public Cart createCart(int user_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT add_cart(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user_id);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return findById(id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Cart findById(int id) throws SQLException {
        String sql = "SELECT * from find_cart_by_id(?)";
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

    @Override
    public Cart createGuestCart() throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT add_guest_cart()";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int id = fetchGeneratedId(statement);
            connection.commit();
            return findById(id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }
    
    private Cart fetchCart(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        int price = resultSet.getInt("price");
        return new Cart(id, user_id, price);
    }
}
