package com.xiong.tblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private Object data;

    private R(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private R(Integer code, Object data){
        this.code = code;
        this.data = data;
    }

    public static R ok(String msg){
        return new R(HttpStatus.OK.value(), msg);
    }

    public static R ok(Object data){
        return new R(HttpStatus.OK.value(), data);
    }

    public static R error(String errMsg){
        return new R(HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg);
    }

    public static R error(Object data){
        return new R(HttpStatus.INTERNAL_SERVER_ERROR.value(), data);
    }

    public static R error(Integer code, String msg){
        return new R(code, msg);
    }

    public static R error(Integer code, Object data){
        return new R(code, data);
    }
}