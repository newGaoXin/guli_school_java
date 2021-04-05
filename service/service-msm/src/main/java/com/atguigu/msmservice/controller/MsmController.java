package com.atguigu.msmservice.controller;

import com.atguigu.commonutlis.utils.R;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msmservice/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("/send/{phone}")
    public R sendCode(@PathVariable String phone){
       boolean send =  msmService.sendCode(phone);
       if (!send){
           R.error().message("短信发送失败!");
       }
       return R.success();
    }

}
