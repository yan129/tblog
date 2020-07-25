package com.xiong.tblog.config;

import com.xiong.tblog.entity.R;
import com.xiong.tblog.utils.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *没有认证时，在这里处理结果，不要重定向
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        R error = R.error(HttpStatus.UNAUTHORIZED.value(), "访问失败!");
        if (authException instanceof InsufficientAuthenticationException){
            error.setMsg("请求失败，请联系管理员!");
        }
        HttpUtil.out(error);
    }
}
