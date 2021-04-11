package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutlis.utils.ResultCode;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.entity.TPayLog;
import com.atguigu.orderservice.mapper.TPayLogMapper;
import com.atguigu.orderservice.service.TPayLogService;
import com.atguigu.orderservice.utils.HttpClient;
import com.atguigu.servicebase.handler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2021-04-08
 */
@Service
@Slf4j
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderServiceImpl tOrderService;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("order_no", orderNo);
        TOrder order = tOrderService.getOne(tOrderQueryWrapper);
        if (Objects.isNull(order)) {
            throw new GuliException(ResultCode.ERROR, "订单不存在！");
        }

        Map m = new HashMap();
        //1、设置支付参数
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");
        //2、HTTPClient来根据URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

        Map<String, String> resultMap = null;

        //client设置参数
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);

            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            resultMap = WXPayUtil.xmlToMap(xml);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4、封装返回结果集
        Map map = new HashMap<String,String>();
        map.put("out_trade_no", orderNo);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", resultMap.get("result_code"));
        map.put("code_url", resultMap.get("code_url"));
        //微信支付二维码2小时过期，可采取2小时未支付取消订单
        //redisTemplate.opsForValue().set(orderNo, map, 120,TimeUnit.MINUTES);
        return map;
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(tOrderQueryWrapper);

        log.info("查询出来的订单 order : {}",order);

        if (Objects.isNull(order)){
            throw new GuliException(ResultCode.ERROR,"订单不存在！");
        }

        Map<String, String> resultMap = null;
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new
                    HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            resultMap.put("out_trade_no",order.getOrderNo());
            //7、返回
//            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(wrapper);

        log.info("查询出来的订单 order : {}",order);

        if(order.getStatus().intValue() == 1) return;

        order.setStatus(1);
        tOrderService.updateById(order);
        //记录支付日志
        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
