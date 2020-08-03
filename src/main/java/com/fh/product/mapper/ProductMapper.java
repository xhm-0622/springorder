package com.fh.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.product.model.Product;
import org.apache.ibatis.annotations.Param;


public interface ProductMapper extends BaseMapper<Product> {

    Long updateStock(@Param("id") Integer id,@Param("count") int count);
}
