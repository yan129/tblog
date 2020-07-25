package com.xiong.tblog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtil {

    public static void out(Object data) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(data));
        writer.flush();
        writer.close();
    }
}
