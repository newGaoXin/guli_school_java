package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-04
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getHotTeacherByLimit(Integer limit);

    List<EduTeacher> getHotTeacher();

    Map<String, Object> getPageListWeb(int current, int limit);
}
