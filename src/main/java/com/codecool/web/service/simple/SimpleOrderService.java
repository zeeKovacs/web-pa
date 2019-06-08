package com.codecool.web.service.simple;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;

import java.sql.SQLException;

public class SimpleOrderService implements OrderService {

    private final OrderDao orderDao;

    public SimpleOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder() throws SQLException {
        return orderDao.createOrder();
    }
}
