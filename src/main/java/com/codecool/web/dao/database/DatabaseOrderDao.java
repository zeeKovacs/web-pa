package com.codecool.web.dao.database;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOrderDao extends AbstractDao implements OrderDao {

    public DatabaseOrderDao(Connection connection) {
        super(connection);
    }


    @Override
    public Order findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return null;
    }

    @Override
    public Order createOrder() throws SQLException {
        return null;
    }

    private Order fetchOrder(ResultSet resultSet) {
        return null;
    }
}
