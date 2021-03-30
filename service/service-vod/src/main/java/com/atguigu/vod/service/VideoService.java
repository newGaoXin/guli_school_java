package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {


    String uploadVideo(MultipartFile file);

    /**
     * 删除阿里云视频
     * @param id
     * @return
     */
    boolean deleteVideoById(String id);

    boolean deleteBatchVideoByIds(List<String> ids);
}
