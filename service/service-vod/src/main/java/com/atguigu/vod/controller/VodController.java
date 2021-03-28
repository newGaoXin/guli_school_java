package com.atguigu.vod.controller;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
@RequestMapping("/vodservice/vod")
@Api(description = "阿里云视频管理")
public class VodController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/uploadVideo")
    @ApiOperation("上传视频")
    public R uploadVideo(@ApiParam(name = "file",value = "file",required = true)@RequestBody MultipartFile file){
        if (Objects.isNull(file)){
            return R.error().message("file不能为空");
        }

        String requestId = videoService.uploadVideo(file);

        if (StringUtils.isEmpty(requestId)){
            return R.error().message("上传视频失败！");
        }

        return R.success().data("data",requestId);
    }
}
