package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.model.Cart;
import com.codecool.web.model.Order;
import com.codecool.web.service.CartService;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.simple.SimpleCartService;
import com.codecool.web.service.simple.SimpleOrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao);

            List<Order> orders = orderService.findAll();

            sendMessage(resp, HttpServletResponse.SC_OK, orders);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao);
            CartDao cartDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(cartDao);

            Order order = null;
            Cart cart = null;
            int cart_id = Integer.valueOf(req.getParameter("cart-id"));
            if (req.getParameter("user-id") != null) {
                int user_id = Integer.valueOf(req.getParameter("user-id"));
                order = orderService.createUserOrder(cart_id, user_id);
                cart = cartService.createCart(user_id);
            } else {
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                order = orderService.createGuestOrder(cart_id, name, email);
                cart = cartService.createGuestCart();
            }

            sendMessage(resp, HttpServletResponse.SC_OK, cart);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao);

            String option = req.getParameter("option");
            int order_id = Integer.parseInt(req.getParameter("order-id"));
            if (option.equals("confirm")) {
                orderService.confirmOrder(order_id);
            } else if (option.equals("complete")) {
                orderService.completeOrder(order_id);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
