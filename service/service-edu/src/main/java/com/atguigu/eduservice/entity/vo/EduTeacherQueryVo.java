package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "讲师分页查询对象",discriminator = "讲师分页查询对象")
@Data
public class EduTeacherQueryVo {

    @ApiModelProperty("讲师名称，右模糊查询")
    private String name;

    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2021-01-01 12:59:59")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2021-01-01 12:59:59")
    private String end;
}
