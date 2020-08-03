package com.fh.sdk;

import java.io.InputStream;

public class MyWXPayConfig extends WXPayConfig{
    @Override
    String getAppID() {
        return "wxa1e44e130a9a8eee";
    }

    @Override
    String getMchID() {
        return "1507758211";
    }

    @Override
    String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        MyDomain domain=new MyDomain();
        return domain;
    }
}
