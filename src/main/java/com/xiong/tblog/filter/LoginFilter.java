package com.xiong.tblog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.tblog.common.VerificationCodeConstants;
import com.xiong.tblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    StringRedisTemplate template;
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String uuid = VerificationCodeConstants.PREFIX + request.getHeader("uuid");
        String targetCode = template.opsForValue().get(uuid);
        // 表单以json数据提交
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            Map<String, String> formData = new HashMap<>();
            try {
                formData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String code = formData.get("verifyCode");
                checkCode(code, targetCode);
            }catch (IOException e){

            }
            String username = formData.get(getUsernameParameter());
            String password = formData.get(getPasswordParameter());
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest =new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            User principal = new User();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            // 表单以普通数据提交
            String code = request.getParameter("verifyCode").trim();
            checkCode(code, targetCode);
            return super.attemptAuthentication(request, response);
        }
    }

    public void checkCode(String code, String targetCode){
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(targetCode)
                || !targetCode.toLowerCase().equals(code.toLowerCase())){
            System.out.println("验证码不正确！");
            // 验证码不正确
            throw new AuthenticationServiceException("验证码不正确！");
        }else {
            template.delete(targetCode);
        }
    }
}
