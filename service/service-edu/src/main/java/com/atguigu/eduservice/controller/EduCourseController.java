package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.bo.EduCourseBO;
import com.atguigu.eduservice.entity.vo.EduCourseInfoVo;
import com.atguigu.eduservice.entity.vo.EduCoursePublicVo;
import com.atguigu.eduservice.entity.vo.EduCourseQueryVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/course")
@Api(description = "课程管理")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程")
    @PostMapping("/addCourse")
    public R addCourse(@ApiParam(name = "course", value = "课程对象", required = true) @RequestBody EduCourseInfoVo course) {
        if (course == null) {
            return R.error().message("课程信息不能为空");
        }


        EduCourseBO eduCourseBO = new EduCourseBO();
        BeanUtils.copyProperties(course, eduCourseBO);

        String courseId = eduCourseService.addCourse(eduCourseBO);

        if (StringUtils.isEmpty(courseId)) {
            return R.error();
        }
        return R.success().data("courseId", courseId);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation("根据课程id获取课程信息")
    public R getCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable(required = true, name = "courseId") String courseId) {
        if(StringUtils.isEmpty(courseId)){
            return R.error().message("课程ID不能为空！");
        }
        EduCourseInfoVo eduCourseInfoVo = eduCourseService.getCourseInfo(courseId);

        return R.success().data("data", eduCourseInfoVo);
    }


    @PostMapping("/updateCourseInfo")
    @ApiOperation("更新课程信息")
    public R updateCourseInfo(@ApiParam(name = "eduCourseInfoVo", value = "课程信息vo类", required = true) @RequestBody EduCourseInfoVo eduCourseInfoVo) {
        String id = eduCourseService.updateCourseInfo(eduCourseInfoVo);

        if (StringUtils.isEmpty(id)) {
            return R.error().message("保存课程失败！");
        }
        return R.success().data("courseId", id);
    }

    @GetMapping("/getCoursePublic/{courseId}")
    @ApiOperation("根据课程id获取课程发布信息")
    public R getCoursePublicByCourseId(@ApiParam(name = "courseId", value = "courseId", required = true) @PathVariable(name = "courseId") String courseId) {
        if(StringUtils.isEmpty(courseId)){
            return R.error().message("课程ID不能为空！");
        }
        EduCoursePublicVo eduCoursePublicVo = eduCourseService.getCoursePublicByCourseId(courseId);
        return R.success().data("data", eduCoursePublicVo);
    }


    @PostMapping("/coursePublic")
    @ApiOperation("课程发布")
    public R coursePublic(@ApiParam(name = "eduCoursePublicVo", value = "eduCoursePublicVo", required = true) @RequestBody EduCoursePublicVo eduCoursePublicVo) {
        if (eduCoursePublicVo == null) {
            return R.error().message("课程发布信息不能为空");
        }
        int aPublic = eduCourseService.coursePublic(eduCoursePublicVo);
        if (aPublic <= 0) {
            return R.error().message("发布课程失败！");
        }
        return R.success();
    }

    @PostMapping("/coursePage/{current}/{limit}")
    @ApiOperation("分页查询课程-带条件参数")
    public R coursePageList(@ApiParam(name = "current", value = "current", required = true) @PathVariable(name = "current") int current,
                            @ApiParam(name = "limit", value = "limit", required = true) @PathVariable("limit") int limit,
                            @ApiParam(name = "eduCourseQueryVo",value = "eduCourseQueryVo",required = true)@RequestBody EduCourseQueryVo eduCourseQueryVo) {
        if (current < 0){
            current = 0;
        }

        if (limit <0){
            limit = 10;
        }

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (eduCourseQueryVo != null){

//            if ()
        }

        Page<EduCourse> page = new Page<>(current,limit);
        IPage<EduCourse> eduCourseIPage = eduCourseService.page(page, queryWrapper);

        return R.success().data("data",eduCourseIPage);
    }

    @DeleteMapping("/delete/{courseId}")
    public R deleteCourse(@ApiParam(name = "",value = "",required = true)@PathVariable String courseId){
        if (StringUtils.isEmpty(courseId)){
            return R.error().message("courseId不存在！");
        }

        eduCourseService.deleteCourse(courseId);

        return R.success();
    }

    @GetMapping("/hotCourseByLimit/{limit}")
    public R getHotCourseByLimit(@PathVariable(value = "limit") Integer limit){
        if (Objects.isNull(limit) || limit == 0){
            return R.error().message("limit不能为空或者为0!");
        }

       List<EduCourse> list = eduCourseService.getHotCourseByLimit(limit);

        return R.success().data("list",list);
    }

    @GetMapping("/{courseId}")
    public R getCourse(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = eduCourseService.getById(courseId);
        return R.success().data("eduCourse",eduCourse);
    }
}

