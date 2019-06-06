package com.codecool.web.service.simple;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.model.Product;
import com.codecool.web.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class SimpleProductService implements ProductService {

    private final ProductDao productDao;

    public SimpleProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }
}
