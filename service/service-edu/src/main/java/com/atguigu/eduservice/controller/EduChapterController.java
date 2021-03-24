package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.vo.EduChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description = "课程章节管理")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterAndVideoList")
    @ApiOperation("获取课程章节和小节List")
    public R getChapterAndVideoList(@ApiParam(name = "courseId",value = "课程id",required = true) String courseId){
        if (StringUtils.isEmpty(courseId)){
            return R.error().message("课程id不能为空");
        }
        List<EduChapterVo> list =  eduChapterService.getChapterAndVideoList(courseId);
        return R.success().data("data",list);
    }


}

