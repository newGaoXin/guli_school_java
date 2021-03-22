package com.atguigu.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级类目
 */
@Data
public class EduOneSubjectDTO {

    private String id;

    private String title;

    private String parentId;

    private List<EduTwoSubjectDTO> children = new ArrayList<>();

}
