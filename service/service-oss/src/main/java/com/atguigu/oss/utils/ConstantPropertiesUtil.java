package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.accessKeyID}")
    private String accessKeyID;

    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    @Value("${aliyun.oss.file.pathHeader}")
    private String pathHeader;

    public static String END_POINT;

    public static String ACCESS_KEY_ID;

    public static String ACCESS_KEY_SECRET;

    public static String BUCKET_NAME;

    public static String PATH_HEADER;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = this.endPoint;
        ACCESS_KEY_ID = this.accessKeyID;
        ACCESS_KEY_SECRET = this.accessKeySecret;
        BUCKET_NAME = this.bucketName;
        PATH_HEADER = this.pathHeader;
    }
}
