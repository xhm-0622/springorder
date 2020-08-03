package com.fh.address.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.address.model.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    List<Address> queryAddressList(Integer id);

    void del(Long id);

    List<Map<String, Object>> queryList();
}
