package com.fh.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.common.ServerResponse;
import com.fh.product.model.Product;

public interface ProductService {
    ServerResponse queryHostProductList();

    ServerResponse queryListNoPage(Page<Product> page);

    Product queryProductById(Integer productId);

    Long updateStock(Integer id, int count);
}
