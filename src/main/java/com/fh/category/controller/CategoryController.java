package com.fh.category.controller;

import com.fh.category.service.CategoryService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查詢
     * @return
     */
   @RequestMapping("queryList")
   @Ignore
    public ServerResponse  queryList(){
       List<Map<String, Object>> list = categoryService.queryList();
       return ServerResponse.success(list);
   }
}
