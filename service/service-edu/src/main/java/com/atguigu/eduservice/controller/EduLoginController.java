package com.atguigu.eduservice.controller;

import com.atguigu.commonutlis.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(description = "登录管理")
@RestController()
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("/login")
    public R login(){
        return R.success().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.success().data(map);
    }
}
