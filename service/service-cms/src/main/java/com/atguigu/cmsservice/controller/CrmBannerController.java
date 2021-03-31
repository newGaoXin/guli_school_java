package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.entity.vo.CrmBannerVo;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutlis.utils.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/cmsservice/crm-banner")
@Api(description = "banner后台管理")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @PostMapping("/pageList/{current}/{limit}")
    @ApiOperation("分页条件查询banner列表")
    public R pageList(@PathVariable(value = "current") Long current, @PathVariable("limit")Long limit){
        if (current < 0){
            current = 0L;
        }

        if (limit < 0) {
            limit = 0L;
        }

        IPage<CrmBanner> page = crmBannerService.pageList(current, limit);
        return R.success().data("data",page);
    }

    @PostMapping("/add")
    @ApiOperation("新增bannner")
    public R addBanner(@RequestBody CrmBannerVo crmBannerVo){
        CrmBanner crmBanner = new CrmBanner();
        BeanUtils.copyProperties(crmBannerVo,crmBanner);

        boolean save = crmBannerService.save(crmBanner);

        if (!save){
            return R.error().message("添加banner失败！");
        }

        return R.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除banner")
    public R deleteBanner(@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            return R.error().message("id不能为空！");
        }
        boolean remove = crmBannerService.removeById(id);
        if (!remove){
            return R.error().message("删除banner失败！");
        }

        return R.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新banner")
    public R update(@RequestBody CrmBanner crmBanner){
        CrmBanner banner = crmBannerService.getById(crmBanner.getId());
        if (Objects.isNull(banner)){
            return R.error().message("banner不存在！");
        }
        boolean update = crmBannerService.updateById(crmBanner);
        if (!update){
            return R.error().message("更新banner失败！");
        }
        return R.success();
    }



}

