package com.atguigu.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    // 上传文件到 阿里云Oss
    String uploadFile(MultipartFile file);
}
