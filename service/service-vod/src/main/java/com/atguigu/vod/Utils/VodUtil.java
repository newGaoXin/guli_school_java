package com.atguigu.vod.Utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class VodUtil {

    public static GetVideoPlayAuthResponse getVideoPlayAuth(String ID){
        GetVideoPlayAuthResponse response = null;
        try {
            DefaultAcsClient client = VodInit.initVodClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(ID);
            response = client.getAcsResponse(request);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");

        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return response;
    }
}
