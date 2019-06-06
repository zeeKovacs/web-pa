package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.SignUpService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public class SimpleSignUpService implements SignUpService {

    private final UserDao userDao;

    public SimpleSignUpService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addGuestUser() throws SQLException, ServiceException {
        try {
            return userDao.addGuestUser();
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public User addUser(String name, String email, String role, String password) throws SQLException, ServiceException {
        try {
            return userDao.addUser(name, email, role, password);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public User updateUser(int id, String name, String email, String role, String password) throws SQLException, ServiceException {
        try {
            return userDao.updateUser(id, name, email, role, password);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}
