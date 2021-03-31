package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutlis.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/educms/banner")
public class BannerApiController {

    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("/getBannerAll")
    public R getBannerAll(){
        List<CrmBanner> list = crmBannerService.getBannerAll();
        return R.success().data("data",list);
    }
}
