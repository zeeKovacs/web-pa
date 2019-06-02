package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface SignUpService {

    User addUser(String name, String email, String role, String password, String phone_number) throws SQLException, ServiceException;

    User addGuestUser() throws SQLException, ServiceException;
}
