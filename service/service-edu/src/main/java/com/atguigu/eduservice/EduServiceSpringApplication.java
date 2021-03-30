package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@EnableFeignClients
@EnableDiscoveryClient
public class EduServiceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduServiceSpringApplication.class,args);
    }
}
