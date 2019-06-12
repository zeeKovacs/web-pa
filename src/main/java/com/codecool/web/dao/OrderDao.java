package com.codecool.web.dao;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    Order confirmOrder(int order_id) throws SQLException;

    Order completeOrder(int order_id) throws SQLException;

    Order findById(int id) throws SQLException;

    List<Order> findAll() throws SQLException;

    Order createUserOrder(int cart_id, int user_id) throws SQLException;

    Order createGuestOrder(int cart_id, String name, String email) throws SQLException;
}
