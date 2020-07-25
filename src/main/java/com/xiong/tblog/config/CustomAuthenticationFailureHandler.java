package com.xiong.tblog.config;

import com.xiong.tblog.entity.R;
import com.xiong.tblog.utils.HttpUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        R error = R.error("登录失败！");
        if (e instanceof LockedException){
            error.setMsg("账户被锁定，请联系管理员!");
        }else if (e instanceof CredentialsExpiredException){
            error.setMsg("密码过期，请联系管理员!");
        }else if (e instanceof AccountExpiredException){
            error.setMsg("账户过期，请联系管理员!");
        }else if (e instanceof DisabledException){
            error.setMsg("账户被禁用，请联系管理员!");
        }else if (e instanceof BadCredentialsException){
            error.setMsg("用户名或者密码输入错误，请重新输入!");
        }
        HttpUtil.out(error);
    }
}
