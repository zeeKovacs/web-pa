package com.codecool.web.service;

import com.codecool.web.model.Order;

import java.sql.SQLException;

public interface OrderService {

   Order createOrder() throws SQLException;
}
