package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.EduChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description = "课程章节管理")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterAndVideoList")
    @ApiOperation("获取课程章节和小节List")
    public R getChapterAndVideoList(@ApiParam(name = "courseId", value = "课程id", required = true) String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            return R.error().message("课程id不能为空");
        }
        List<EduChapterVo> list = eduChapterService.getChapterAndVideoList(courseId);
        return R.success().data("data", list);
    }

    @PostMapping("/save")
    @ApiOperation("根据课程id保存章节")
    public R saveChapterByCourseId(@ApiParam(name = "eduChapter", value = "eduChapter", required = true) @RequestBody EduChapter eduChapter) {
        boolean save = eduChapterService.save(eduChapter);
        if (!save) {
            return R.error().message("保存章节失败！");
        }
        return R.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改章节")
    public R updateChapter(@ApiParam(name = "eduChapter", value = "eduChapter", required = true) @RequestBody EduChapter eduChapter) {
        boolean update = eduChapterService.updateById(eduChapter);
        if (!update) {
            return R.error().message("修改章节失败！");
        }
        return R.success();
    }

    @DeleteMapping("/delete/{chapterId}")
    @ApiOperation("删除章节")
    public R deleteChapter(@ApiParam(name = "chapterId",value = "chapterId",required = true) @PathVariable(value = "chapterId",required = true) String chapterId){
       boolean delete =  eduChapterService.deleteChapter(chapterId);
       if (!delete){
           R.error().message("删除章节失败");
       }
       return R.success();
    }


}

