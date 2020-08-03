package com.fh.order.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.fh.User.model.User;

import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.mapper.OrderMapper;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.util.BigDecimalUtil;
import com.fh.util.IdUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private ProductService productService;

    @Override
    public ServerResponse queryOrderList(List<Cart> cartList, Integer addressId, Integer payType, User user) {
        //生成orderId 根据雪花算法
        String orderId = IdUtil.createId();
        //创建订单详情集合
        List<OrderInfo> orderInfos = new ArrayList<>();
        //库存不足
        List<String> underStock = new ArrayList<>();
        //价格
        BigDecimal totalPrice = new BigDecimal("0.00");
        //遍历循环 购物车
        for (Cart cart : cartList) {
            //查询当前商品
            Product product = productService.queryProductById(cart.getProductId());
            if (product.getStock() <= cart.getCount()) {
                //库存不充足
                underStock.add(cart.getName());
            }
            //库存是否充足
            Long count = productService.updateStock(product.getId(), cart.getCount());
            if (count == 1) {
                //生成订单详情
                OrderInfo orderInfo = orderInfo(cart, orderId);
                //新增
                orderInfos.add(orderInfo);
                BigDecimal bigDecimal = BigDecimalUtil.mul(cart.getPrice().toString(), cart.getCount() + "");
                totalPrice = BigDecimalUtil.add(bigDecimal, totalPrice);
            } else {
                //不充足
                underStock.add(cart.getName());
            }
        }
        if (orderInfos != null && orderInfos.size() == cartList.size()) {
            //库存都足  保存订单详细
            for (OrderInfo orderInfo : orderInfos) {
                orderInfoMapper.insert(orderInfo);
                //更新redis购物车
                updateRedisCart(orderInfo,user);
            }
            //  生成订单
            Order order = new Order();
            order.setCreateDate(new Date());
            order.setPayType(payType);
            order.setAddressId(addressId);
            order.setId(orderId);
            order.setUserId(user.getId());
            order.setTotalPrice(totalPrice);
            order.setStatus(SystemConstant.ORDER_STATUS_WAIT);
            orderMapper.insert(order);
            return ServerResponse.success(orderId);
        } else {
            return ServerResponse.error(underStock);

        }
    }




    private OrderInfo   orderInfo(Cart cart,String orderId){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setName(cart.getName());
        orderInfo.setFilePath(cart.getFilePath());
        orderInfo.setPrice(cart.getPrice());
        orderInfo.setOrderId(orderId);
        orderInfo.setProductId(cart.getProductId());
        orderInfo.setCount(cart.getCount());
        return orderInfo;
    }


    private void updateRedisCart(OrderInfo orderInfo,User user){
        String cartJson = RedisUtil.hget(SystemConstant.CRAT_KEY + user.getId(), orderInfo.getProductId().toString());
        if (StringUtils.isNotEmpty(cartJson)) {
            Cart cart1 = JSONObject.parseObject(cartJson, Cart.class);
            if (cart1.getCount() <= orderInfo.getCount()) {
                //删除购物车中该商品
                RedisUtil.hdel(SystemConstant.CRAT_KEY+user.getId(), orderInfo.getProductId().toString());

            } else {
                //更新购物车
                cart1.setCount(cart1.getCount() - orderInfo.getCount());
                String s = JSONObject.toJSONString(cart1);
                RedisUtil.hset(SystemConstant.CRAT_KEY + user.getId(), orderInfo.getProductId().toString(), s);
            }

        }
    }
}
