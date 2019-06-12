package com.codecool.web.service.simple;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class SimpleOrderService implements OrderService {

    private final OrderDao orderDao;

    public SimpleOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return orderDao.findAll();
    }

    @Override
    public Order completeOrder(int order_id) throws SQLException {
        return orderDao.completeOrder(order_id);
    }

    @Override
    public Order confirmOrder(int order_id) throws SQLException {
        return orderDao.confirmOrder(order_id);
    }

    @Override
    public Order createUserOrder(int cart_id, int user_id) throws SQLException {
        return orderDao.createUserOrder(cart_id, user_id);
    }

    @Override
    public Order createGuestOrder(int cart_id, String name, String email) throws SQLException {
        return orderDao.createGuestOrder(cart_id, name, email);
    }
}
