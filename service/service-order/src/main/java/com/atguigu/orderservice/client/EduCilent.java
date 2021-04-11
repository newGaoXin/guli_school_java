package com.atguigu.orderservice.client;

import com.atguigu.commonutlis.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
@Component
public interface EduCilent {

    @GetMapping("/eduservice/course/{courseId}")
    public R getCourse(@PathVariable("courseId") String courseId);

    @ApiOperation("根据id查询讲师")
    @GetMapping("/eduservice/teacher/getTeacher/{id}")
    R getTeacher(@PathVariable("id") String id);
}
