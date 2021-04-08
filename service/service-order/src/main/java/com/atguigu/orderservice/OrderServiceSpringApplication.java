package com.atguigu.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@EnableFeignClients
@EnableDiscoveryClient
public class OrderServiceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceSpringApplication.class,args);
    }
}
