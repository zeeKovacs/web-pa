package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.SignUpService;
import com.codecool.web.service.exception.ServiceException;
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
            SignUpService signUpService = new SimpleSignUpService(userDao);

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String phone_number = req.getParameter("phone_number");
            String role = "USER";

            User user = (User) req.getSession().getAttribute("user");
            if (user == null) {
                user = signUpService.addUser(name, email, role, password, phone_number);
            } else if (user.getRole().equals("GUEST")) {
                int id = user.getId();
                user = signUpService.updateUser(id, name, email, role, password, phone_number);
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
