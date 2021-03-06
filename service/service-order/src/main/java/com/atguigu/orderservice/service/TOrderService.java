package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2021-04-08
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String userId);

    void updateOrderStatus(Map<String, String> map);
}
