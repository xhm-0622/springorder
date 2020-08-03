package com.fh.city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.category.model.Category;
import com.fh.city.model.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface CityMapper extends BaseMapper<City> {
    List<Map<String, Object>> queryList();
}
