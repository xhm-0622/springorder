package com.fh.cart.service;

import com.fh.User.model.User;
import com.fh.common.ServerResponse;

public interface CartService {
    ServerResponse buy(Integer productId, Integer count, User request);
}
