package com.codecool.web.dao.database;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOrderDao extends AbstractDao implements OrderDao {

    public DatabaseOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public Order confirmOrder(int order_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT confirm_order(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_id);
            int returned_id = fetchGeneratedId(statement);
            connection.commit();
            return findById(returned_id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Order completeOrder(int order_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT complete_order(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_id);
            int returned_id = fetchGeneratedId(statement);
            connection.commit();
            return findById(returned_id);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Order findById(int id) throws SQLException {
        String sql = "SELECT * from find_order_by_id(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchOrder(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        String sql = "SELECT * from find_all_orders()";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(fetchOrder(resultSet));
            }
            return orders;
        }
    }

    @Override
    public Order createUserOrder(int cart_id, int user_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT add_user_order(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_id);
            statement.setInt(2, user_id);
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
    public Order createGuestOrder(int cart_id, String name, String email) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "SELECT add_guest_order(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cart_id);
            statement.setString(2, name);
            statement.setString(3, email);
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

    private Order fetchOrder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int user_id = resultSet.getInt("user_id");
        int cart_id = resultSet.getInt("cart_id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        boolean confirmed = resultSet.getBoolean("confirmed");
        boolean complete = resultSet.getBoolean("complete");
        return new Order(id, user_id, cart_id, name, email, confirmed, complete);
    }
}
