package com.codecool.web.servlet;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.dao.database.DatabaseCartItemDao;
import com.codecool.web.service.CartItemService;
import com.codecool.web.service.simple.SimpleCartItemService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/cartItem")
public class CartItemServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartItemDao cartItemDao = new DatabaseCartItemDao(connection);
            CartItemService cartItemService = new SimpleCartItemService(cartItemDao);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartItemDao cartItemDao = new DatabaseCartItemDao(connection);
            CartItemService cartItemService = new SimpleCartItemService(cartItemDao);



        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
