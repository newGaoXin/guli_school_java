package com.atguigu.eduservice.controller;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/getHotCourseAndTeacher")
    public R getHotCourseAndTeacher(){
        List<EduCourse> list = eduCourseService.getHotCourse();
        List<EduTeacher> teacherList = eduTeacherService.getHotTeacher();

        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList",list);
        map.put("teacherList",teacherList);

        return R.success().data(map);
    }
}
