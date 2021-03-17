package com.atguigu.eduservice.entity.ready;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EduSubjectExcelReady {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
