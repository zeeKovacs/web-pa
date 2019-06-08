package com.codecool.web.dao;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    Order findById(int id) throws SQLException;

    List<Order> findAll() throws SQLException;

    Order createOrder() throws SQLException;
}
