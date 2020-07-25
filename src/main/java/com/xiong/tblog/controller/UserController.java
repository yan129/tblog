package com.xiong.tblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.tblog.common.SendSms;
import com.xiong.tblog.common.SmsConstants;
import com.xiong.tblog.common.VerificationCodeConstants;
import com.xiong.tblog.entity.R;
import com.xiong.tblog.entity.User;
import com.xiong.tblog.service.UserService;
import com.xiong.tblog.utils.VerificationCodeGenerationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SendSms sendSms;
    @Autowired
    private StringRedisTemplate template;

    @GetMapping("/getUser")
    public User getUser(){
        log.info("====================");
        return userService.getOne(new QueryWrapper<User>().eq("username", "15728276040"));
    }

    @GetMapping("/getVerificationCode")
    public R getVerificationCode(HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setContentType("image/jpeg");
        String uuid = UUID.randomUUID().toString();
        VerificationCodeGenerationUtil code = new VerificationCodeGenerationUtil();
        BufferedImage image = code.getImage();
        String text = code.getText();
        template.opsForValue().set(VerificationCodeConstants.PREFIX + uuid, text, VerificationCodeConstants.EXPIRE_TIME, TimeUnit.SECONDS);
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("codeUrl", VerificationCodeGenerationUtil.output(image));
        return R.ok(map);
    }

    @PostMapping("/getRegisterCode")
    public R getRegisterCode(@RequestBody @Valid User user, BindingResult errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return R.error(message);
        }
        Boolean hasKey = template.hasKey(SmsConstants.PREFIX + user.getUsername());
        if (hasKey)
            return R.error("请勿频繁操作！");
        //手机号校验成功发送注册码
        return sendSms.send(user.getUsername()) == true ? R.ok("发送成功！") : R.error("服务出错！");
    }

    @PostMapping("/register")
    public R register(@Valid User user, BindingResult errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return R.error(message);
        }
        return userService.register(user.getUsername(), user.getPassword());
    }
}
