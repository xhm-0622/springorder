package com.fh.pay;

import com.fh.common.ServerResponse;
import com.fh.sdk.MyWXPayConfig;
import com.fh.sdk.WXPay;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {

    @RequestMapping("pays")
    public ServerResponse pays(String orderId, BigDecimal price) {
        MyWXPayConfig config = new MyWXPayConfig();
        try {
            WXPay wxPay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "腾讯充值中心-QQ会员充值");
            data.put("out_trade_no", orderId);
            data.put("device_info", "WEB");
            data.put("fee_type", "CNY");
            data.put("total_fee", "1");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", "12");
            Map<String, String> res = wxPay.unifiedOrder(data);
            //
            if (!res.get("return_code").equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error("微信支付错误：" + res.get("return_msg"));
            }
            if (!res.get("result_code").equalsIgnoreCase("SUCCESS")) {
                return ServerResponse.error("微信支付错误：" + res.get("err_code_des"));
            }
            String url = res.get("code_url");
            return ServerResponse.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return  ServerResponse.error();
        }
    }
}

