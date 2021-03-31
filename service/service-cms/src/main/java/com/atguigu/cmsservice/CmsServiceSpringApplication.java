package com.atguigu.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu")
public class CmsServiceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsServiceSpringApplication.class,args);
    }
}
