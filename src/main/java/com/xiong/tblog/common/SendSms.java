package com.xiong.tblog.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SendSms {

    @Value("${zhenzi.apiUrl}")
    private String apiUrl;  // 个人开发者请求地址

    @Value("${zhenzi.appId}")
    private String appId;

    @Value("${zhenzi.appSecret}")
    private String appSecret;

    @Autowired
    private StringRedisTemplate template;

    public boolean send(String mobile){
        boolean flag = false;
        try {
            // 生成6位注册码
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            // 发送短信
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            // 剩余0条余额信息
            if ("0".equals(client.balance())){
                log.error("短信剩余数量：" + client.balance() + "条");
                return flag;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String message = "T博客, 您的注册码为:"+ code +"，该码有效期为5分钟，该码只能使用一次!";
            Map<String,String> params = new HashMap<String,String>();
            Map<String,Object> parsMap = new HashMap<>();
            params.put("message", message);
            params.put("number", mobile);
            String sendMsg = client.send(params);
            // 解析sendMsg返回的json数据，并放入Map
            Map readValue = objectMapper.readValue(sendMsg, parsMap.getClass());
            // 发送短信code: 发送状态，0为成功。非0为发送失败
            int status = (Integer) readValue.get("code");
            if (status == 0){
                // 发送成功将注册码缓存到Redis
                template.opsForValue().set(SmsConstants.PREFIX + mobile, code, SmsConstants.EXPIRE_TIME, TimeUnit.SECONDS);
                flag = true;
            }else{
                log.error("fail：短信服务异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
