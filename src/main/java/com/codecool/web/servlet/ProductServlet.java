package com.codecool.web.servlet;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.dao.database.DatabaseProductDao;
import com.codecool.web.model.Product;
import com.codecool.web.service.ProductService;
import com.codecool.web.service.simple.SimpleProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends AbstractServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);

            List<Product> products = productService.findAll();

            sendMessage(resp, HttpServletResponse.SC_OK, products);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);

            int product_id = Integer.valueOf(req.getParameter("product-id"));
            Product product = productService.setAvailability(product_id);

            sendMessage(resp, HttpServletResponse.SC_OK, product);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
