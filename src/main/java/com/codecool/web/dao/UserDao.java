package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User addGuestUser() throws SQLException;

    List<User> findAll() throws SQLException;

    User findById(int id) throws SQLException;

    User findByEmail(String email) throws SQLException;

    User addUser(String name, String email, String role, String password, String phone_number) throws SQLException;

    User updateUser(int id, String name, String email, String role, String password, String phone_number) throws SQLException;
}
