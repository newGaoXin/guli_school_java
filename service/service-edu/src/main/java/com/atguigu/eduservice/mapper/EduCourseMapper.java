package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.EduCoursePublicVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    EduCoursePublicVo getCoursePublicByCourseId(String courseId);
}
