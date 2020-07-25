package com.xiong.tblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan(basePackages = "com.xiong.tblog.mapper")
//将RSA配置类加入bean
//@EnableConfigurationProperties(RsaKeyProperties.class)
public class TblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TblogApplication.class, args);
    }

}
