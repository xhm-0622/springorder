package com.fh.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.ProductMapper;
import com.fh.product.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public ServerResponse queryHostProductList() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("isHost",1);
        List<Product> products = productMapper.selectList(wrapper);
        return ServerResponse.success(products);
    }

    @Override
    public ServerResponse queryListNoPage(Page<Product> page) {
        IPage<Product> productIPage = productMapper.selectPage(page, null);
        return ServerResponse.success(productIPage);
    }

    @Override
    public Product queryProductById(Integer productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public Long updateStock(Integer id, int count) {
        return productMapper.updateStock(id,count);
    }


}
