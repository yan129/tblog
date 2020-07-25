package com.xiong.tblog.config;

import com.xiong.tblog.filter.RegisterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 解决Redis在拦截器取值为null
     * 知道拦截器执行在bean实例化前执行的，那么我们就让拦截器执行的时候实例化拦截器Bean，
     * 在拦截器配置类里面先实例化拦截器，然后再获取
     * @return
     */
    @Bean
    public RegisterInterceptor registerInterceptor(){
        return new RegisterInterceptor();
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(registerInterceptor()).addPathPatterns("/user/register");
    }

    //配置允许跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }
}
