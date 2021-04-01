package com.atguigu.cmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@EnableSwagger2
public class CmsServiceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsServiceSpringApplication.class,args);
    }
}
