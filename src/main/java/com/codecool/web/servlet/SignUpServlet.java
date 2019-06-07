package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.CartService;
import com.codecool.web.service.SignUpService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCartService;
import com.codecool.web.service.simple.SimpleSignUpService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/signUp")
public final class SignUpServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            SignUpService signUpService = new SimpleSignUpService(userDao);
            CartService cartService = new SimpleCartService(cartDao);


            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = "USER";

            User user = signUpService.addUser(name, email, role, password);

            int cart_id = 0;
            if (req.getParameter("cart-id") != null) {
                int user_id = user.getId();
                cart_id = Integer.valueOf(req.getParameter("cart-id"));
                cartService.assignCartToUser(cart_id, user_id);
            }

            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_NO_CONTENT, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
