package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.commonutlis.utils.R;
import com.atguigu.orderservice.client.EduCilent;
import com.atguigu.orderservice.client.TeacherClient;
import com.atguigu.orderservice.client.UcentMemberClient;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.entity.api.Course;
import com.atguigu.orderservice.entity.api.Teacher;
import com.atguigu.orderservice.entity.api.UcenterMember;
import com.atguigu.orderservice.mapper.TOrderMapper;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    private EduCilent eduCilent;

    @Autowired
    private UcentMemberClient ucentMemberClient;

//    @Autowired
//    private TeacherClient teacherClient;

    @Override
    public String createOrder(String courseId, String userId) {
        R ucentMemberR = ucentMemberClient.getUcentMember(userId);
        UcenterMember ucenterMember = JSON.parseObject(JSON.toJSONString(ucentMemberR.getData().get("UcenterMember")), UcenterMember.class);

        TOrder tOrder = new TOrder();
        tOrder.setMobile(ucenterMember.getMobile());
        tOrder.setNickname(ucenterMember.getNickname());
        tOrder.setMemberId(ucenterMember.getId());

        R courseR = eduCilent.getCourse(courseId);
        Course eduCourse = JSON.parseObject(JSON.toJSONString(courseR.getData().get("eduCourse")), Course.class);

        R teacherR = eduCilent.getTeacher(eduCourse.getTeacherId());
        Teacher teacher = JSON.parseObject(JSON.toJSONString(teacherR.getData().get("item")), Teacher.class);

        tOrder.setCourseCover(eduCourse.getCover());
        tOrder.setTeacherName(teacher.getName());
        tOrder.setCourseId(eduCourse.getId());
        tOrder.setCourseTitle(eduCourse.getTitle());
        tOrder.setIsDeleted(false);
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setPayType(1);
        tOrder.setStatus(0);

        super.baseMapper.insert(tOrder);


        return tOrder.getOrderNo();
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {

    }
}
