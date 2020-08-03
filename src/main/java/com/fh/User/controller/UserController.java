package com.fh.User.controller;

import com.fh.User.model.User;
import com.fh.User.service.UserService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户是否存在
     * @param name
     * @return
     */
    @RequestMapping("queryByName")
    @Ignore
    public ServerResponse queryByName(String name){

        return userService.queryByName(name);
    }

    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     */
    @RequestMapping("queryByPhone")
    @Ignore
    public ServerResponse queryByPhone(String   phone){
        return userService.queryByPhone(phone);
    }

    /**
     * 注册
     * @param user
     * @return
     */
   @RequestMapping("register")
   @Ignore
    public ServerResponse register(User user){

       return userService.register(user);
   }

    /**
     * 登录
     * @param user
     * @return
     */
   @RequestMapping("login")
   @Ignore
    public ServerResponse login(User user){

       return userService.login(user);
   }

    /**
     * 用户是否登录
     * @param request
     * @return
     */
   @RequestMapping("checkLogin")
   public ServerResponse checkLogin(HttpServletRequest request){
       User user = (User) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
       if (user==null){
           return ServerResponse.error();
       }
       return ServerResponse.success();
   }

    /**
     * 退出
     * @return
     */
    @RequestMapping("out")
    public ServerResponse out(HttpServletRequest request){
        String token = (String) request.getSession().getAttribute(SystemConstant.REDIS_KEY);
        RedisUtil.del(SystemConstant.REDIS_KEY+token);
        //清除session中的token
        request.getSession().removeAttribute(SystemConstant.REDIS_KEY);
        return ServerResponse.success();
    }


}
