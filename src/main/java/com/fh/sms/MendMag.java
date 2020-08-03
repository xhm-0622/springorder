package com.fh.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.util.AliyunSmsUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("sms")
public class MendMag {
    /**
     * 进行验证码判断
     * @param phone
     * @return
     */
    @RequestMapping("getCode")
    @Ignore
    public ServerResponse getCode(String phone) {
    //生成随机数
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        SendSmsResponse sendSmsResponse = AliyunSmsUtil.sendSms(phone, code);
        if(sendSmsResponse.getCode()!=null && "OK".equals(sendSmsResponse.getCode())){
            RedisUtil.setEx(phone,code, SystemConstant.REDIS_EXPIRY_TIME);
            return  ServerResponse.success();
        }

        return ServerResponse.error();
    }
}
