package com.fh.order.service;

import com.fh.User.model.User;
import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;

import java.util.List;

public interface OrderService {
    ServerResponse queryOrderList(List<Cart> cartList, Integer addressId, Integer payType, User user);
}
