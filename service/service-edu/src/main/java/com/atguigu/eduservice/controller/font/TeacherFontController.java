package com.atguigu.eduservice.controller.font;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.EduTeacherFontInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/eduservice/teacherFont")
@CrossOrigin
public class TeacherFontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/pageList/{current}/{limit}")
    public R pageList(@PathVariable int current,@PathVariable int limit){
        if (current < 1){
            current = 1;
        }

        Map<String,Object> data = teacherService.getPageListWeb(current,limit);

        return R.success().data("data",data);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            R.error().message("讲师id不能为空！");
        }

        EduTeacher eduTeacher = teacherService.getById(id);

        if (Objects.isNull(eduTeacher)){
            R.error().message("课程不存在！");
        }

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<EduCourse> list = eduCourseService.list(queryWrapper);

        EduTeacherFontInfoVo eduTeacherFontInfoVo = new EduTeacherFontInfoVo();
        BeanUtils.copyProperties(eduTeacher,eduTeacherFontInfoVo);
        eduTeacherFontInfoVo.setEduCourseList(list);

        return R.success().data("teacherInfo",eduTeacherFontInfoVo);
    }
}
