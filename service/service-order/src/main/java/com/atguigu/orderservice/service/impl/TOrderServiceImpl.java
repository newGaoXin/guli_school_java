package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.commonutlis.utils.R;
import com.atguigu.orderservice.client.CourseCilent;
import com.atguigu.orderservice.client.UcentMemberClient;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.entity.api.Course;
import com.atguigu.orderservice.entity.api.UcenterMember;
import com.atguigu.orderservice.mapper.TOrderMapper;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-04-08
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseCilent courseCilent;

    @Autowired
    private UcentMemberClient ucentMemberClient;

    @Override
    public String createOrder(String courseId, String userId) {
        R ucentMemberR = ucentMemberClient.getUcentMember(userId);
        UcenterMember ucenterMember = JSON.parseObject(JSON.toJSONString(ucentMemberR.getData().get("UcenterMember")), UcenterMember.class);

        TOrder tOrder = new TOrder();
        tOrder.setMobile(ucenterMember.getMobile());
        tOrder.setNickname(ucenterMember.getNickname());
        tOrder.setMemberId(ucenterMember.getId());

        R courseR = courseCilent.getCourse(courseId);
        Course eduCourse = JSON.parseObject(JSON.toJSONString(courseR.getData().get("eduCourse")), Course.class);

        tOrder.setCourseCover(eduCourse.getCover());
        tOrder.setCourseId(eduCourse.getId());
        tOrder.setCourseTitle(eduCourse.getTitle());
        tOrder.setIsDeleted(false);
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setPayType(1);

        super.baseMapper.insert(tOrder);


        return tOrder.getOrderNo();
    }
}
