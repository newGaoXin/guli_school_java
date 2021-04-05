package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xin
 * @since 2021-04-03
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    int register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    UcenterMember getLoginInfo(String memberId);

    UcenterMember getUcenterByOpenId(String openid);
}
