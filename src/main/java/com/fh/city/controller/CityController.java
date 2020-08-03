package com.fh.city.controller;

import com.fh.city.service.CityService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 查詢
     * @return
     */
   @RequestMapping("queryList")
   @Ignore
    public ServerResponse  queryList(){
       List<Map<String, Object>> list = cityService.queryList();
       return ServerResponse.success(list);
   }



}
