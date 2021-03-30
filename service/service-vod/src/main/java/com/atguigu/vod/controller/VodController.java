package com.atguigu.vod.controller;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/vodservice/vod")
@Api(description = "阿里云视频管理")
@CrossOrigin
public class VodController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/uploadVideo")
    @ApiOperation("上传视频")
    public R uploadVideo(@ApiParam(name = "file", value = "file", required = true) @RequestBody MultipartFile file) {
        if (Objects.isNull(file)) {
            return R.error().message("file不能为空");
        }

        String videoId = videoService.uploadVideo(file);

        if (StringUtils.isEmpty(videoId)) {
            return R.error().message("上传视频失败！");
        }

        return R.success().data("data", videoId);
    }

    // 阿里云删除视频
    @DeleteMapping("/delete/{id}")
    public R deleteVideoById(@PathVariable(value = "id") String id) {
        boolean delete = videoService.deleteVideoById(id);
        if (!delete) {
            return R.error().message("删除视频失败!");
        }
        return R.success();
    }

    // 阿里云批量删除视频
    @DeleteMapping("/deleteBatch")
    public R deleteBatchVideoByIds(List<String> ids) {
        boolean delete = videoService.deleteBatchVideoByIds(ids);
        if (!delete) {
            return R.error().message("删除视频失败!");
        }
        return R.success();
    }
}
