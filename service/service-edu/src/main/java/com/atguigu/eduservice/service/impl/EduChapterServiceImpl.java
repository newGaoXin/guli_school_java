package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutlis.utils.ResultCode;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.EduChapterVo;
import com.atguigu.eduservice.entity.vo.EduVideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<EduChapterVo> getChapterAndVideoList(String courseId) {
        // 根据 课程获取章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        // 根据课程获取小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = eduVideoService.list(eduVideoQueryWrapper);

        List<EduChapterVo> list = new ArrayList<>(eduChapters.size());

        for (EduChapter eduChapter : eduChapters) {
            EduChapterVo eduChapterVo = new EduChapterVo();
            BeanUtils.copyProperties(eduChapter, eduChapterVo);

            ArrayList<EduVideo> eduChapterVos = new ArrayList<>();
            Iterator<EduVideo> iterator = videoList.iterator();
            while (iterator.hasNext()){
                EduVideo video = iterator.next();
                EduVideo eduVideo = new EduVideo();
                if (eduChapterVo.getId().equals(video.getChapterId())){
                    BeanUtils.copyProperties(video,eduVideo);
                    eduChapterVos.add(eduVideo);
                    iterator.remove();
                }
            }

            eduChapterVo.setChildren(eduChapterVos);
            list.add(eduChapterVo);
        }

        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        if (eduChapter == null){
            throw new GuliException(ResultCode.ERROR,"chapterId不存在");
        }

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        List<EduVideo> videoList = eduVideoService.list(eduVideoQueryWrapper);

        if (videoList.size()>0){
            throw new GuliException(ResultCode.ERROR,"小节存在，无法删除章节");
        }

        int delete = baseMapper.deleteById(chapterId);
        if (delete <= 0){
            throw new GuliException(ResultCode.ERROR,"删除章节失败");
        }
        return delete > 0;
    }
}
