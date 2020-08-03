package com.fh.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.User.model.User;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("buy")
    public ServerResponse buy(Integer productId, Integer count, @UserAnnotation User user){
        return cartService.buy(productId,count,user);
    }

    @RequestMapping("queryCartProductCount")
    public ServerResponse queryCartProductCount(@UserAnnotation User user){
        //从redis 中的 hvals 获取数据
        List<String> stringList = RedisUtil.hget(SystemConstant.CRAT_KEY + user.getId());
        long cartCount=0;
        //判断不能为空 不能小于0
        if (stringList != null && stringList.size()>0){
            for (String str : stringList) {
                //把json串对象
                Cart cart = JSONObject.parseObject(str, Cart.class);
                //进行拼接
                cartCount+=cart.getCount();
            }
            return ServerResponse.success(cartCount);
        }else {
            return ServerResponse.success(0);
        }
    }

    /**
     * 查询数据
     * @param user
     * @return
     */
    @RequestMapping("queryCartList")
    public ServerResponse queryCartList(@UserAnnotation User user){
        //从redis 中的 hvals 获取数据
        List<String> stringList = RedisUtil.hget(SystemConstant.CRAT_KEY + user.getId());
        List<Cart> cartList = new ArrayList<>();
        if (stringList != null && stringList.size()>0){
            for (String str : stringList) {
                //把json串对象
                Cart cart = JSONObject.parseObject(str, Cart.class);
                //进行新增
                cartList.add(cart);

            }
             return ServerResponse.success(cartList);
        }else {

            return ServerResponse.error(ServerEnum.CART_IS_NOT);
        }
    }

    /**
     * 删除
     * @param productId
     * @param user
     * @return
     */
    @RequestMapping("delProductCart")
    public ServerResponse delProductCart(Integer productId,@UserAnnotation User user){
        RedisUtil.hdel(SystemConstant.CRAT_KEY+user.getId(),productId.toString());
        return ServerResponse.success();
    }

    /**
     * 批量删除
     * @param list
     * @param user
     * @return
     */
    @RequestMapping("delBatch")
    public ServerResponse delBatch(@RequestParam("idList") List list, @UserAnnotation User user){
        for (int i = 0; i < list.size(); i++) {
            RedisUtil.hdel(SystemConstant.CRAT_KEY+user.getId(),list.get(i).toString());
        }
        return ServerResponse.success();
    }
}
