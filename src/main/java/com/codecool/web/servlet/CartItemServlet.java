package com.codecool.web.servlet;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.dao.database.DatabaseCartItemDao;
import com.codecool.web.model.CartItem;
import com.codecool.web.service.CartItemService;
import com.codecool.web.service.simple.SimpleCartItemService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cartItem")
public class CartItemServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartItemDao cartItemDao = new DatabaseCartItemDao(connection);
            CartItemService cartItemService = new SimpleCartItemService(cartItemDao);

            int cart_id = Integer.valueOf(req.getParameter("cart-id"));
            List<CartItem> items = cartItemService.findCartItemsByCartId(cart_id);

            sendMessage(resp, HttpServletResponse.SC_OK, items);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartItemDao cartItemDao = new DatabaseCartItemDao(connection);
            CartItemService cartItemService = new SimpleCartItemService(cartItemDao);

            int cart_id = Integer.valueOf(req.getParameter("cart-id"));
            int product_id = Integer.valueOf(req.getParameter("product-id"));
            int quantity = Integer.valueOf(req.getParameter("quantity"));

            CartItem cartItem = cartItemService.addToCart(cart_id, product_id, quantity);

            sendMessage(resp, HttpServletResponse.SC_OK, cartItem);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CartItemDao cartItemDao = new DatabaseCartItemDao(connection);
            CartItemService cartItemService = new SimpleCartItemService(cartItemDao);

            int item_id = Integer.valueOf(req.getParameter("item-id"));

            cartItemService.removeItemFromCart(item_id);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
