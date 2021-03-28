package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    int addCourseDescription(EduCourseDescription eduCourseDescription);
}
