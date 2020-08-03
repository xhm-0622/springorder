package com.fh.address.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_address")
public class Address {
    private  Integer id;
    private String name;
    private String phone;
    private Integer cityId1;
    private Integer cityId2;
    private Integer cityId3;
    private int cityId4;
    private String cityName;
    private String detailedArea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCityId1() {
        return cityId1;
    }

    public void setCityId1(Integer cityId1) {
        this.cityId1 = cityId1;
    }

    public Integer getCityId2() {
        return cityId2;
    }

    public void setCityId2(Integer cityId2) {
        this.cityId2 = cityId2;
    }

    public Integer getCityId3() {
        return cityId3;
    }

    public void setCityId3(Integer cityId3) {
        this.cityId3 = cityId3;
    }

    public int getCityId4() {
        return cityId4;
    }

    public void setCityId4(int cityId4) {
        this.cityId4 = cityId4;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDetailedArea() {
        return detailedArea;
    }

    public void setDetailedArea(String detailedArea) {
        this.detailedArea = detailedArea;
    }
}
