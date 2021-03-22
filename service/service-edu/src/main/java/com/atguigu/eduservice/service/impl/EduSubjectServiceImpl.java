package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.dto.EduOneSubjectDTO;
import com.atguigu.eduservice.entity.dto.EduTwoSubjectDTO;
import com.atguigu.eduservice.entity.ready.EduSubjectExcelReady;
import com.atguigu.eduservice.listener.EduSubjectExcelReadyListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void readyExcel(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(file.getInputStream(), EduSubjectExcelReady.class,new EduSubjectExcelReadyListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EduOneSubjectDTO> getSubjectListTree() {
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectDTOList = baseMapper.selectList(oneWrapper);

        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id",0);
        List<EduSubject> twoSubjectDTOList = baseMapper.selectList(twoWrapper);

        ArrayList<EduOneSubjectDTO> tree = new ArrayList<>();

        for (EduSubject oneEduSubject : oneSubjectDTOList) {
            EduOneSubjectDTO oneSubjectDTO = new EduOneSubjectDTO();
            BeanUtils.copyProperties(oneEduSubject,oneSubjectDTO);

            ArrayList<EduTwoSubjectDTO> eduTwoSubjectDTOS = new ArrayList<>();
            for (EduSubject eduSubject : twoSubjectDTOList) {
                if (eduSubject.getParentId().equals(oneSubjectDTO.getId())){
                    EduTwoSubjectDTO eduTwoSubjectDTO = new EduTwoSubjectDTO();
                    BeanUtils.copyProperties(eduSubject,eduTwoSubjectDTO);
                    eduTwoSubjectDTOS.add(eduTwoSubjectDTO);
                }

            }
            oneSubjectDTO.setChildren(eduTwoSubjectDTOS);
            tree.add(oneSubjectDTO);
        }
        return tree;
    }
}
