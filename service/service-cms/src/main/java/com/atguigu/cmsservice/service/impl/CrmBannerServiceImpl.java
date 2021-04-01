package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.mapper.CrmBannerMapper;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.boot.Banner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-03-31
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public IPage<CrmBanner> pageList(Long current,Long limit) {
        Page<CrmBanner> crmBannerPage = new Page<>(current, limit);
        IPage<CrmBanner> page = super.baseMapper.selectPage(crmBannerPage,null);
        return page;
    }

    @Cacheable(value = "banner",key = "'getBannerAll'")
    @Override
    public List<CrmBanner> getBannerAll() {
        List<CrmBanner> crmBanners = super.baseMapper.selectList(null);
        return crmBanners;
    }
}
