package com.xiong.tblog.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {

    /**
     * 逻辑删除
     * 1.配置插件
     * 2.在实体属性上面添加注解 @TableLogic
     * 3.编写逻辑删除的方法
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
