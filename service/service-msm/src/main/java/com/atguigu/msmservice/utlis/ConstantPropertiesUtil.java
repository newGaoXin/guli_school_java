package com.atguigu.msmservice.utlis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.keyid}")
    private String accessKeyID;

    @Value("${aliyun.keysecret}")
    private String accessKeySecret;

    public static String ACCESS_KEY_ID;

    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID =  this.accessKeyID;
        ACCESS_KEY_SECRET = this.accessKeySecret;
    }
}
