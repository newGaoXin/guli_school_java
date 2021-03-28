package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.EduVideoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(description = "小节管理")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/save")
    @ApiOperation("保存小节")
    public R saveVideo(@ApiParam(name = "eduVideo",value = "eduVideo",required = true)@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if (!save){
            return R.error().message("保存小节失败");
        }

        return R.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改小节")
    public R updateVideo(@ApiParam(name = "eduVideo",value = "eduVideo",required = true)@RequestBody EduVideo eduVideo){
        boolean update = eduVideoService.updateById(eduVideo);
        if (!update){
            return R.error().message("修改小节失败!");
        }

        return R.success();
    }

    @DeleteMapping("/delete/{videoId}")
    @ApiOperation("删除小节")
    public R deleteVideo(@ApiParam(name = "videoId",value = "videoId",required = true)@PathVariable(name = "videoId")String videoId){
        boolean remove = eduVideoService.removeById(videoId);
        if (!remove){
            return R.error().message("删除小节失败");
        }

        return R.success();

    }


}

