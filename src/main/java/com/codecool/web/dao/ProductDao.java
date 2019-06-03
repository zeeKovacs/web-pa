package com.codecool.web.dao;

import com.codecool.web.model.Product;

import java.sql.SQLException;

public interface ProductDao {

    Product findProductById(int id) throws SQLException;
}
