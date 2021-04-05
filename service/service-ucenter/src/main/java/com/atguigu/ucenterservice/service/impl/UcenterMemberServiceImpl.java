package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutlis.utils.JwtUtils;
import com.atguigu.commonutlis.utils.MD5;
import com.atguigu.commonutlis.utils.ResultCode;
import com.atguigu.servicebase.handler.exception.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2021-04-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public int register(RegisterVo registerVo) {
        // 判断验证码是否存在
        String code = redisTemplate.opsForValue().get("code:" + registerVo.getMobile());
        if (StringUtils.isEmpty(code)){
            throw new GuliException(ResultCode.ERROR,"验证码不存在！");
        }

        // 查询数据库手机号是否注册过
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",registerVo.getMobile());
        Integer count = super.baseMapper.selectCount(ucenterMemberQueryWrapper);
        if (count > 0){
            throw new GuliException(ResultCode.ERROR,"手机号已注册");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(registerVo.getMobile());
        ucenterMember.setPassword(MD5.encrypt(registerVo.getPassword()));
        ucenterMember.setNickname(registerVo.getNickname());
        ucenterMember.setIsDisabled(false);
        int insert = super.baseMapper.insert(ucenterMember);
        redisTemplate.delete("code:" + registerVo.getMobile());

        return insert;
    }

    @Override
    public String login(LoginVo loginVo) {
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile", loginVo.getMobile());
        UcenterMember ucenterMember = super.baseMapper.selectOne(ucenterMemberQueryWrapper);

        if (Objects.isNull(ucenterMember)){
            throw new GuliException(ResultCode.ERROR,"用户名或密码错误！");
        }

        if (ucenterMember.getIsDisabled().equals(0)){
            throw new GuliException(ResultCode.ERROR,"用户名或密码错误!");
        }

        if (!MD5.encrypt(loginVo.getPassword()).equals(ucenterMember.getPassword())){
            throw new GuliException(ResultCode.ERROR,"用户名或密码错误!");
        }

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    @Override
    public UcenterMember getLoginInfo(String memberId) {
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("id",memberId);
        UcenterMember ucenterMember = super.baseMapper.selectOne(ucenterMemberQueryWrapper);
        if (Objects.isNull(ucenterMember)){
            throw new GuliException(ResultCode.ERROR,"用户信息异常!");
        }
        return ucenterMember;
    }

    @Override
    public UcenterMember getUcenterByOpenId(String openid) {
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("openid",openid);
        UcenterMember ucenterMember = super.baseMapper.selectOne(ucenterMemberQueryWrapper);
        return ucenterMember;
    }
}
