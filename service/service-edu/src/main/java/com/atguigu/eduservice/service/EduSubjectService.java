package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.dto.EduOneSubjectDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    void readyExcel(MultipartFile file,EduSubjectService eduSubjectService);

    /**
     * 获取课程分类列表树
     * @return
     */
    List<EduOneSubjectDTO> getSubjectListTree();
}
