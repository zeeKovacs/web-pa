package com.codecool.web.service;

import com.codecool.web.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    List<Product> findAll() throws SQLException;

    Product setAvailability(int product_id) throws SQLException;
}
