package com.fh.User.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.User.mapper.UserMapper;
import com.fh.User.model.User;
import com.fh.common.ServerResponse;
import com.fh.util.JwtTokenUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public ServerResponse queryByName(String name) {
        //判断用户名不能为空
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return ServerResponse.success();
        }
        return ServerResponse.error("用户已存在");
    }

    @Override
    public ServerResponse queryByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userPhone",phone);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return ServerResponse.success();
        }
        return ServerResponse.error("手机号已存在");
    }

    @Override
    public ServerResponse register(User user) {
        //判断手机号的redis的值
        String phone = RedisUtil.get(user.getUserPhone());
        if (phone==null){
            ServerResponse.error("验证码已失效");
        }
        //判断二次密码是否相等
        if (!phone.equals(user.getCode())){
            ServerResponse.error("验证码错误");
        }
        //新增用户
        userMapper.insert(user);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(User user) {
        //判断用户和密码不能为空
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getName());
        wrapper.or();
        wrapper.eq("userPhone",user.getName());
        User userDB = userMapper.selectOne(wrapper);
        if (userDB==null){
            return ServerResponse.error("账号或手机号错误");
        }
        if (!user.getPassword().equals(userDB.getPassword())){
            return ServerResponse.error("密码错误");
        }
        //在用户登录成功后生成token
        String token =null;
        try {
            String encode = URLEncoder.encode(JSONObject.toJSONString(userDB), "utf-8");
            token = JwtTokenUtils.sign(encode);
            RedisUtil.setEx(SystemConstant.REDIS_KEY+token,token,SystemConstant.REDIS_EXPIRY_TIME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ServerResponse.success(token);
    }
}
