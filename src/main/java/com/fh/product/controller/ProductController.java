package com.fh.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 查询热销商品
     * @return
     */
    @RequestMapping("queryHostProductList")
    @Ignore
    public ServerResponse queryHostProductList(){
        return productService.queryHostProductList();
    }

    /**
     * 分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("queryListNoPage")
    @Ignore
    public ServerResponse queryListNoPage(Long currentPage,Long pageSize){
        Page<Product> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        return productService.queryListNoPage(page);
    }
}
