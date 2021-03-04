package com.atguigu.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.eduservice.mapper")
public class MyBatisPlusConfig {

    /**
     * 实现逻辑删除
     * @return
     */
    @Bean
    public ISqlInjector getISqlInjector(){
        return new LogicSqlInjector();
    }
}
