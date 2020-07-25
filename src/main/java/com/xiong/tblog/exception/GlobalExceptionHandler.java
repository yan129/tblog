package com.xiong.tblog.exception;

import com.xiong.tblog.entity.R;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public R authException(AuthenticationException e){
        if (e instanceof AuthenticationServiceException){
            return R.error("验证码填写错误！");
        }
        return null;
    }
}
