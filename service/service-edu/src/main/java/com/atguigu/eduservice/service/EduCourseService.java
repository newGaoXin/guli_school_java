package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.bo.EduCourseBO;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.EduCourseInfoVo;
import com.atguigu.eduservice.entity.vo.EduCoursePublicVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(EduCourseBO eduCourseBO);

    EduCourseInfoVo getCourseInfo(String courseId);

    String updateCourseInfo(EduCourseInfoVo eduCourseInfoVo);

    EduCoursePublicVo getCoursePublicByCourseId(String courseId);

    int coursePublic(EduCoursePublicVo eduCoursePublicVo);

    void deleteCourse(String courseId);

    List<EduCourse> getHotCourseByLimit(Integer limit);

    List<EduCourse> getHotCourse();

    Map<String, Object> pageListFont(int current, int limit, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseInfoFont(String id);
}
