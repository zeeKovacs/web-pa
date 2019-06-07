package com.codecool.web.service.simple;

import com.codecool.web.dao.CartItemDao;
import com.codecool.web.service.CartItemService;

public class SimpleCartItemService implements CartItemService {

    private final CartItemDao cartItemDao;

    public SimpleCartItemService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }
}
