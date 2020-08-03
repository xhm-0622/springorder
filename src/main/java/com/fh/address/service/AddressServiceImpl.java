package com.fh.address.service;

import com.fh.address.mapper.AddressMapper;
import com.fh.address.model.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;
    @Override
    public List<Address> queryAddressList(Integer id) {

        return addressMapper.queryAddressList(id);
    }


    @Override
    public void addArea(Address userArea) {
        addressMapper.insert(userArea);
    }

    @Override
    public void updateArea(Address userArea) {

        addressMapper.updateById(userArea);
    }

    @Override
    public void del(Long id) {

        addressMapper.del(id);
    }




    @Override
    public List<Map<String, Object>> queryList() {
        List<Map<String ,Object>>  allList =   addressMapper.queryList();
        List<Map<String ,Object>>  parentList = new ArrayList<Map<String, Object>>();
        for (Map  map : allList) {
            if(map.get("pid").equals(0)){
                parentList.add(map);
            }
        }
        selectChildren(parentList,allList);
        return parentList;
    }



    public void selectChildren(List<Map<String ,Object>>  parentList, List<Map<String ,Object>>  allList){
        for (Map<String, Object> pmap : parentList) {
            List<Map<String ,Object>>  childrenList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> amap : allList) {
                if(pmap.get("id").equals(amap.get("pid"))){
                    childrenList.add(amap);
                }
            }
            if(childrenList!=null && childrenList.size()>0){
                pmap.put("children",childrenList);
                selectChildren(childrenList,allList);
            }

        }
    }

}
