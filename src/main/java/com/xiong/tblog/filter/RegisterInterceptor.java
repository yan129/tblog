package com.xiong.tblog.filter;

import com.xiong.tblog.common.SmsConstants;
import com.xiong.tblog.entity.R;
import com.xiong.tblog.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate template;

    // 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        if ("POST".equals(request.getMethod()) && "/user/register".equals(request.getServletPath())){
            // 拦截注册请求，检验注册码
            String code = request.getParameter("registerCode").trim();
            String mobile = SmsConstants.PREFIX + request.getParameter("username");
            String targetCode =template.opsForValue().get(mobile);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(targetCode)
                    || !targetCode.equals(code)){
                //注册码不正确
                R error = R.error("验证码填写错误！");
                HttpUtil.out(error);
            }else {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 注册完成删除Redis的注册码
        String mobile = request.getParameter("username");
        template.delete(SmsConstants.PREFIX + mobile);
    }
}
