package com.atguigu.cmsservice.service;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.boot.Banner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-31
 */
public interface CrmBannerService extends IService<CrmBanner> {

    IPage<CrmBanner> pageList(Long current, Long limit);

    List<CrmBanner> getBannerAll();
}
