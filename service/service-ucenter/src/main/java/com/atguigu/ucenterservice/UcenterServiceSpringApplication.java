package com.atguigu.ucenterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.atguigu"})
public class UcenterServiceSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterServiceSpringApplication.class,args);
    }
}
