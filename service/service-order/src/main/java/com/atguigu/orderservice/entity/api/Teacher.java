package com.atguigu.orderservice.entity.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable {
    private static final long serialVersionUID = -8433875000540252761L;

    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

}
