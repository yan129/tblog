package com.xiong.tblog.filter;

import com.xiong.tblog.common.VerificationCodeConstants;
import com.xiong.tblog.entity.R;
import com.xiong.tblog.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VerificationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate template;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ("POST".equals(request.getMethod())
                && "/user/doLogin".equals(request.getServletPath())){
            String code = request.getParameter("verifyCode").trim();
            String uuid = request.getHeader("uuid");
            String targetCode = template.opsForValue().get(VerificationCodeConstants.PREFIX + uuid);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(targetCode)
                    || !targetCode.toLowerCase().equals(code.toLowerCase())){
                R error = R.error("验证码填写错误！");
                HttpUtil.out(error);
                return ;
            }else{
                filterChain.doFilter(request, response);
                template.delete(VerificationCodeConstants.PREFIX + uuid);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }

}
