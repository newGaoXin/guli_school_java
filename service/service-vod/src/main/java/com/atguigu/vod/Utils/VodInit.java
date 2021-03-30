package com.atguigu.vod.Utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

@Component
public class VodInit {

    private static final DefaultAcsClient client = null ;

    public static DefaultAcsClient initVodClient() throws ClientException {

        if (client == null){
            synchronized (VodInit.class){
                if (client == null){
                    String regionId = "cn-shanghai";  // 点播服务接入区域
                    String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
                    String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
                    DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
                    DefaultAcsClient client = new DefaultAcsClient(profile);
                    return client;
                }
            }
        }

        return client;

    }
}
