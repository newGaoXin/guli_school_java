package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/eduservice/eduSubject")
@CrossOrigin
@Api(description = "课程管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubjectByReadyExcel")
    @ApiOperation("读取Excel批量添加课程")
    public R AddSubjectByReadyExcel(@ApiParam(name = "file",value = "Excel文件",required = true)@RequestBody MultipartFile file){
        if (file  == null){
            R.error().message("Excel文件不存在");
        }
        subjectService.readyExcel(file,subjectService);
        return R.success();
    }

}

