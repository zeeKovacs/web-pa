package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.model.Cart;
import com.codecool.web.service.CartService;
import com.codecool.web.service.simple.SimpleCartService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/cart")
public class CartServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartDao userDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(userDao);

            int user_id = 0;
            Cart cart = null;
            if (req.getParameter("user-id") != null) {
                user_id = Integer.valueOf(req.getParameter("user-id"));
                cart = cartService.findCartByUserId(user_id);
            } else {
                int cart_id = Integer.valueOf(req.getParameter("cart-id"));
                cart = cartService.findById(cart_id);
            }

            sendMessage(resp, HttpServletResponse.SC_OK, cart);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartDao userDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(userDao);

            int user_id = 0;
            if (req.getParameter("cart-id") != null) {
                user_id = Integer.valueOf(req.getParameter("user-id"));
            }
            Cart cart = null;

            if (user_id == 0) {
                cart = cartService.createGuestCart();
            } else {
                cart = cartService.createCart(user_id);
            }

            sendMessage(resp, HttpServletResponse.SC_OK, cart);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
