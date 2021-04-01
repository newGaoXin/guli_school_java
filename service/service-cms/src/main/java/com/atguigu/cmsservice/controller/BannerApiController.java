package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutlis.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class BannerApiController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getBannerAll")
    public R getBannerAll(){
        List<CrmBanner> list = crmBannerService.getBannerAll();
        return R.success().data("data",list);
    }
}
