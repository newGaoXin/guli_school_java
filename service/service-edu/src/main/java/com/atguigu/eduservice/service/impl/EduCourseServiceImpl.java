package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutlis.utils.CourseStatus;
import com.atguigu.commonutlis.utils.ResultCode;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.bo.EduCourseBO;
import com.atguigu.eduservice.entity.vo.EduCourseInfoVo;
import com.atguigu.eduservice.entity.vo.EduCoursePublicVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public String addCourse(EduCourseBO eduCourseBO) {
        // 保存课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(eduCourseBO, eduCourse);

        int insert = this.baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(200, "保存课程错误！");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setEduCourseId(eduCourse.getId());
        eduCourseDescription.setDescription(eduCourseBO.getDescription());
        int save = eduCourseDescriptionService.addCourseDescription(eduCourseDescription);

        if (save <= 0) {
            throw new GuliException(200, "保存课程简介错误！");
        }
        return eduCourse.getId();
    }

    @Override
    public EduCourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = this.baseMapper.selectById(courseId);

        EduCourseInfoVo eduCourseInfoVo = new EduCourseInfoVo();
        BeanUtils.copyProperties(eduCourse,eduCourseInfoVo);

        QueryWrapper<EduCourseDescription> descriptionQueryWrapper = new QueryWrapper<>();
        descriptionQueryWrapper.eq("edu_course_id",courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getOne(descriptionQueryWrapper);
        if (Objects.nonNull(eduCourseDescription)){
            eduCourseInfoVo.setDescription(eduCourseDescription.getDescription());
        }


        return eduCourseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String updateCourseInfo(EduCourseInfoVo eduCourseInfoVo) {

        // 1.更新课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(eduCourseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update <= 0){
            throw new GuliException(ResultCode.ERROR,"更新课程失败");
        }

        //2.根据id查询课程详情
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("edu_course_id",eduCourse.getId());
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getOne(eduCourseDescriptionQueryWrapper);
        if (eduCourseDescription == null ){
            throw new GuliException(ResultCode.ERROR,"更新课程失败");
        }
        // 3.更新课程详情
        eduCourseDescription.setDescription(eduCourseInfoVo.getDescription());
        boolean save = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!save) {
            throw new GuliException(ResultCode.ERROR,"更新课程失败");
        }

        return eduCourse.getId();
    }

    @Override
    public EduCoursePublicVo getCoursePublicByCourseId(String courseId) {
        EduCoursePublicVo coursePublicByCourseId = baseMapper.getCoursePublicByCourseId(courseId);
        if (coursePublicByCourseId == null) {
            throw new GuliException(ResultCode.ERROR,"课程发布信息不存在");
        }
        return coursePublicByCourseId;
    }

    @Override
    public int coursePublic(EduCoursePublicVo eduCoursePublicVo) {
        String id = eduCoursePublicVo.getId();
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null){
            throw  new GuliException(ResultCode.ERROR,"课程不存在");
        }
        eduCourse.setStatus(CourseStatus.NORMAL);
        int update = baseMapper.updateById(eduCourse);

        return update;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteCourse(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        if (eduCourse == null){
            throw new GuliException(ResultCode.ERROR,"课程不存在！");
        }

        // TODO 删除小节 阿里云视频

        // 删除关联的小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",eduCourse.getId());
        boolean remove = eduVideoService.remove(eduVideoQueryWrapper);
        if (!remove){
            throw new GuliException(ResultCode.ERROR,"删除关联小节失败!");
        }

        // 删除关联章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",eduCourse.getId());
        boolean chapterRemove = eduChapterService.remove(chapterQueryWrapper);
        if (!chapterRemove){
            throw new GuliException(ResultCode.ERROR,"删除关联章节失败");
        }

        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("edu_course_id",eduCourse.getId());
        boolean courseDescriptionRemove = eduCourseDescriptionService.remove(eduCourseDescriptionQueryWrapper);
        if (!courseDescriptionRemove){
            throw new GuliException(ResultCode.ERROR,"删除关联课程详情失败!");
        }
    }
}
