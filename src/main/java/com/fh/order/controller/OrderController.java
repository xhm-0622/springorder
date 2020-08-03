package com.fh.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.fh.User.model.User;
import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("queryList")
    public ServerResponse queryList(String carts, Integer addressId, Integer payType, @UserAnnotation User user){
        List<Cart> cartList = new ArrayList<>();
        //判断
        if (StringUtils.isNotEmpty(carts)){
            cartList=JSONObject.parseArray(carts,Cart.class);
        }
        return orderService.queryOrderList(cartList,addressId,payType,user);
    }
}
