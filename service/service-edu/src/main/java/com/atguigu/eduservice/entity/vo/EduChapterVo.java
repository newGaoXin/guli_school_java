package com.atguigu.eduservice.entity.vo;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 章节 vo类
 */
@Data
public class EduChapterVo {

    @ApiModelProperty(value = "章节ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;


    @ApiModelProperty(value = "小节集合")
    private List<EduVideo> children;

}
