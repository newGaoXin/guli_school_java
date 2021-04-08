package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutlis.utils.JwtUtils;
import com.atguigu.commonutlis.utils.R;
import com.atguigu.ucenterservice.entity.LoginInfo;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xin
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        if (Objects.isNull(loginVo)) {
            return R.error().message("登录信息不完整");
        }

        if (StringUtils.isEmpty(loginVo.getMobile()) || StringUtils.isEmpty(loginVo.getPassword())) {
            return R.error().message("登录信息不完整");
        }
        String token = ucenterMemberService.login(loginVo);
        return R.success().data("data",token);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        if (registerVo == null) {
            return R.error().message("注册信息不完整！");
        }

        if (StringUtils.isEmpty(registerVo.getCode()) || StringUtils.isEmpty(registerVo.getMobile())
                || StringUtils.isEmpty(registerVo.getNickname()) || StringUtils.isEmpty(registerVo.getPassword())) {
            return R.error().message("注册信息不完整！");
        }

        int register = ucenterMemberService.register(registerVo);
        if (register <= 0) {
            return R.error().message("注册失败！");
        }


        return R.success();
    }

    @PostMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)){
            return R.error().message("用户信息异常！");
        }
        UcenterMember ucenterMember = ucenterMemberService.getLoginInfo(memberId);
        LoginInfo loginInfo = new LoginInfo();
        BeanUtils.copyProperties(ucenterMember,loginInfo);
        return R.success().data("data",loginInfo);
    }

    @GetMapping("/{userId}")
    public R getUcentMember(@PathVariable("userId")String userId){
        UcenterMember ucenterMember = ucenterMemberService.getById(userId);
        return R.success().data("UcenterMember",ucenterMember);
    }

}

