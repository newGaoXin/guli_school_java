package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-04
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public List<EduTeacher> getHotTeacherByLimit(Integer limit) {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("sort");
        eduTeacherQueryWrapper.last("limit " + limit);
        List<EduTeacher> teacherList = super.baseMapper.selectList(eduTeacherQueryWrapper);
        return teacherList;
    }

    @Cacheable(value = "idnex",key = "'getHotTeacher'")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("sort");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = super.baseMapper.selectList(eduTeacherQueryWrapper);
        return teacherList;
    }

    @Override
    public Map<String, Object> getPageListWeb(int current, int limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
//        eduTeacherQueryWrapper.orderByDesc("");
        super.baseMapper.selectPage(eduTeacherPage, eduTeacherQueryWrapper);

        HashMap<String, Object> data = new HashMap<>();
        data.put("current",eduTeacherPage.getCurrent());
        data.put("pages",eduTeacherPage.getPages());
        data.put("records",eduTeacherPage.getRecords());
        data.put("size",eduTeacherPage.getSize());
        data.put("total",eduTeacherPage.getTotal());
        data.put("hasNext",eduTeacherPage.hasNext());
        data.put("hasPrevious",eduTeacherPage.hasPrevious());

        return data;
    }
}
