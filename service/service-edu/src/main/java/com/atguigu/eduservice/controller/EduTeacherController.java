package com.atguigu.eduservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.EduTeacherQueryVo;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-04
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.success().data("items", list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean remove = eduTeacherService.removeById(id);

        if (remove) {
            return R.success();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师方法
     *
     * @param current 当前页
     * @param limit   每页记录书
     * @return
     */
    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R page(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit) {
        // 创建 page 对象
        Page<EduTeacher> page = new Page<>(current, limit);
        // 调用方法实现分页
        // 调用方法的时候，底层封装，把分页所有数据封装到 page 对象中
        eduTeacherService.page(page, null);

        long total = page.getTotal();
        List<EduTeacher> list = page.getRecords();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", list);

        return R.success().data(map);
    }


    @ApiOperation("分页条件讲师查询")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                  @RequestBody(required = false) EduTeacherQueryVo eduTeacherQueryVo) {
        Page<EduTeacher> page = new Page<>(current, limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(eduTeacherQueryVo.getName())) {
            wrapper.likeRight("name", eduTeacherQueryVo.getName());
        }

        if (!StringUtils.isEmpty(eduTeacherQueryVo.getLevel())) {
            wrapper.eq("level", eduTeacherQueryVo.getLevel());
        }

        if (!StringUtils.isEmpty(eduTeacherQueryVo.getBegin())) {
            wrapper.eq("gmt_create", eduTeacherQueryVo.getBegin());
        }

        if (!StringUtils.isEmpty(eduTeacherQueryVo.getEnd())) {
            wrapper.eq("gmt_modified", eduTeacherQueryVo.getEnd());
        }

        eduTeacherService.page(page, wrapper);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", page.getTotal());
        map.put("rows", page.getRecords());

        return R.success().data(map);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.success();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        if (teacher != null) {
            return R.success().data("item", teacher);
        } else {
            return R.error();
        }
    }

    @ApiOperation("修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = eduTeacherService.updateById(eduTeacher);

        if (update) {
            return R.success();
        } else {
            return R.error();
        }
    }
}

