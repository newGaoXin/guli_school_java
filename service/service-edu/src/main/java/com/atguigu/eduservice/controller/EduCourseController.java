package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.bo.EduCourseBO;
import com.atguigu.eduservice.entity.vo.EduCourseVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程")
    @PostMapping("/addChapter")
    public R addCourse(@ApiParam(name = "eduCourseVo", value = "课程对象", required = true) @RequestBody() EduCourseVo eduCourseVo) {
        if (eduCourseVo == null) {
            return R.error().message("课程信息不能为空");
        }


        EduCourseBO eduCourseBO = new EduCourseBO();
        BeanUtils.copyProperties(eduCourseVo, eduCourseBO);

        int add = eduCourseService.addCourse(eduCourseBO);

        if (add > 0) {
            return R.success();
        }
        return R.error().message("添加课程失败");
    }

}

