package com.fh.address.service;

import com.fh.address.model.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {

    List<Address> queryAddressList(Integer id);


    List<Map<String, Object>> queryList();

    void addArea(Address userArea);

    void updateArea(Address userArea);

    void del(Long id);
}
