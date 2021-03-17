package com.atguigu.oss.controller;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/file")
@Api(description = "阿里OSS")
@CrossOrigin
public class OssFileController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    public R uploadFile(@ApiParam(name = "file",value = "文件",required = true) @RequestBody MultipartFile file){
        if (file == null){
            return R.error().message("文件不能为空");
        }

       String url =  ossService.uploadFile(file);

       if (StringUtils.isEmpty(url)){
           return R.error();
       }
       return R.success().data("url",url);
    }
}
