package com.atguigu.orderservice.controller;


import com.atguigu.commonutlis.utils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.entity.status.OrderPayStatus;
import com.atguigu.orderservice.service.TOrderService;
import com.atguigu.orderservice.service.TPayLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
@Slf4j
public class TPayLogController {

    @Autowired
    private TPayLogService tPayLogService;

    @Autowired
    private TOrderService tOrderService;


    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo")String orderNo){
       Map<String,Object> map =  tPayLogService.createNative(orderNo);
       log.info("createNative: {}",map);
       return R.success().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo")String orderNo){
       Map<String,String> map =  tPayLogService.queryPayStatus(orderNo);
       if (map == null){
           return R.error().message("支付错误！");
       }
        log.info("queryPayStatus: {}",map);
       if (map.get("trade_state").equals(OrderPayStatus.SUCCESS.getLabel())){
           tPayLogService.updateOrderStatus(map);

           return R.success().code(OrderPayStatus.SUCCESS.getCode()).message("支付成功!");
       }

      return R.success().code(OrderPayStatus.WAIT.getCode()).message("支付中");
    }
}

