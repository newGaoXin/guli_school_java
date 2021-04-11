package com.atguigu.orderservice.controller;


import com.atguigu.commonutlis.utils.JwtUtils;
import com.atguigu.commonutlis.utils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService tOrderService;

    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = tOrderService.createOrder(courseId,userId);
        if (StringUtils.isEmpty(orderNo)){
            R.error().message("创建订单失败！");
        }
        return R.success().data("orderNo",orderNo);
    }

    @GetMapping("/{orderNo}")
    public R getOrderByOrderNo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("order_no",orderNo);
        TOrder tOrder = tOrderService.getOne(tOrderQueryWrapper);
        return R.success().data("order",tOrder);
    }

}

