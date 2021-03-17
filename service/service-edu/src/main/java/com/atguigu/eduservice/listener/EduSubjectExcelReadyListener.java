package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.ready.EduSubjectExcelReady;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class EduSubjectExcelReadyListener extends AnalysisEventListener<EduSubjectExcelReady> {

    private EduSubjectService eduSubjectService;

    public EduSubjectExcelReadyListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(EduSubjectExcelReady eduSubjectExcelReady, AnalysisContext analysisContext) {
        String oneSubjectName = eduSubjectExcelReady.getOneSubjectName();
        EduSubject oneSubject = this.queryOneSubject(oneSubjectName);
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(oneSubjectName);
            eduSubjectService.save(oneSubject);
        }

        // 获取一级的id 保存进二级中
        String pId = oneSubject.getId();
        String twoSubjectName = eduSubjectExcelReady.getTwoSubjectName();

        EduSubject twoSubject = this.queryTwoSubject(twoSubjectName, pId);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setTitle(twoSubjectName);
            twoSubject.setParentId(pId);
            eduSubjectService.save(twoSubject);
        }

    }

    public EduSubject queryOneSubject(String oneSubject){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubject);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    public EduSubject queryTwoSubject(String twoSubject,String pId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("title",twoSubject);
        wrapper.eq("parent_id",pId);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
