package com.atguigu.eduservice.controller.font;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/courseFont")
public class CourseFontController{

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/pageList/{current}/{limit}")
    public R pageList(@PathVariable int current, @PathVariable int limit,
                      @RequestBody(required = false) CourseQueryVo courseQueryVo){
        if (current < 1){
            current = 1;
        }
        if (limit < 8){
            limit = 8;
        }

        Map<String, Object> data = eduCourseService.pageListFont(current,limit,courseQueryVo);

        return R.success().data("page",data);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable String id){

        CourseWebVo courseWebVo = eduCourseService.getCourseInfoFont(id);


        return R.success().data("courseInfo",courseWebVo);
    }
}
