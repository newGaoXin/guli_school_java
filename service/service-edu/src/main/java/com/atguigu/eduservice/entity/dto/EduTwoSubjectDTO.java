package com.atguigu.eduservice.entity.dto;

import lombok.Data;

/**
 * 二级课程分类
 */
@Data
public class EduTwoSubjectDTO {

    private String id;

    private String title;

    private String parentId;
}
