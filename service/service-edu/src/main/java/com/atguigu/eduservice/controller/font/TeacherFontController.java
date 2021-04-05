package com.atguigu.eduservice.controller.font;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherFont")
@CrossOrigin
public class TeacherFontController {

    @Autowired
    private EduTeacherService teacherService;

    @PostMapping("/pageList/{current}/{limit}")
    public R pageList(@PathVariable int current,@PathVariable int limit){
        if (current < 1){
            current = 1;
        }

        Map<String,Object> data = teacherService.getPageListWeb(current,limit);

        return R.success().data("data",data);
    }
}
