package com.fh.address.controller;


import com.alibaba.fastjson.JSONObject;
import com.fh.User.model.User;
import com.fh.address.service.AddressService;
import com.fh.address.model.Address;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressService service;

    @RequestMapping("queryCityList")
    public ServerResponse queryCityList(String token){
        User user=null;
        try {
            String encode = URLDecoder.decode(JwtTokenUtils.getuser(token),"utf-8");
             user= JSONObject.parseObject(encode, User.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Address> list = service.queryAddressList(user.getId());

        Map<String,Object> map = new HashMap();

        map.put("list",list);
        return ServerResponse.success(map);
    }


    @RequestMapping("queryList")
    @Ignore
    public ServerResponse  queryList(){
        List<Map<String, Object>> list = service.queryList();
        return ServerResponse.success(list);
    }

    @RequestMapping("addArea")
    @Ignore
    public ServerResponse addArea( Address userArea){

        service.addArea(userArea);
        return ServerResponse.success();
    }

    @RequestMapping("updateArea")
    @Ignore
    public ServerResponse updateArea(Address userArea) {
        service.updateArea(userArea);
        return ServerResponse.success();
    }


    @RequestMapping("del")
    @Ignore
    public ServerResponse del(Long id) {
        service.del(id);
        return ServerResponse.success();
    }


}
