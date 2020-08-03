package com.fh.cart.service;

import com.alibaba.fastjson.JSONObject;
import com.fh.User.model.User;
import com.fh.cart.model.Cart;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductService productService;
    @Override
    public ServerResponse buy(Integer productId, Integer count, User user) {
        //是否有该商品
       Product product=productService.queryProductById(productId);
       if (product==null){
           return ServerResponse.error(ServerEnum.PRODUCT_NOT_EXIT);
       }
       //是否上架
        if (product.getStatus()==0){
            return ServerResponse.error(ServerEnum.PRODUCT_NOT_STATUS.getMsg());
        }
        //验证购物车是否有该商品

        boolean exist = RedisUtil.exists(SystemConstant.CRAT_KEY+user.getId() , product.getId().toString());
        if (exist==false){
            //初始化对象
            Cart cart = new Cart();
            //给每个对象赋值
            cart.setProductId(productId);
            cart.setCount(count);
            cart.setName(product.getName());
            cart.setFilePath(product.getFilePath());
            cart.setPrice(product.getPrice());
            //把新增的数据放在缓存当中
            RedisUtil.hset(SystemConstant.CRAT_KEY+user.getId(),productId.toString(), JSONObject.toJSONString(cart));
        }else {
           //如果有则 修改
            String json = RedisUtil.hget(SystemConstant.CRAT_KEY+user.getId() , productId.toString());
            Cart cart = JSONObject.parseObject(json, Cart.class);
            cart.setCount(cart.getCount()+count);
            //如果 新增  需要更新缓存中的数据
            RedisUtil.hset(SystemConstant.CRAT_KEY+user.getId(),productId.toString(), JSONObject.toJSONString(cart));
        }
        return ServerResponse.success();
    }
}
