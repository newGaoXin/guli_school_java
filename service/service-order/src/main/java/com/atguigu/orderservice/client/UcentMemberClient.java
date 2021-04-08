package com.atguigu.orderservice.client;

import com.atguigu.commonutlis.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UcentMemberClient {

    @GetMapping("/ucenterservice/member/{userId}")
    R getUcentMember(@PathVariable("userId")String userId);
}
