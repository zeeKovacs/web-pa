package com.codecool.web.servlet;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.simple.SimpleOrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/orders")
public class OrderServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao);

            //Order order = createOrder();

            //sendMessage(resp, HttpServletResponse.SC_OK, order);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
