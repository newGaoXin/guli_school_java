package com.atguigu.ucenterservice.controller;

import com.atguigu.commonutlis.utils.JwtUtils;
import com.atguigu.commonutlis.utils.ResultCode;
import com.atguigu.servicebase.handler.exception.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.utils.ConstantPropertiesUtil;
import com.atguigu.ucenterservice.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/login")
    public String login() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String wxOpenRedirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            wxOpenRedirectUrl = URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        String url = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, wxOpenRedirectUrl, state);

        return "redirect:" + url;
    }

    @GetMapping("/callback")
    public String callback(String code, String state) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
            throw new GuliException(ResultCode.ERROR, "错误！");
        }

        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl, wxOpenAppId,
                wxOpenAppSecret, code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenUrl---" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        HashMap jsonMap = gson.fromJson(result, HashMap.class);
        String access_token = (String) jsonMap.get("access_token");
        String openid = (String) jsonMap.get("openid");

        UcenterMember ucenterMember = ucenterMemberService.getUcenterByOpenId(openid);
        if (ucenterMember == null) {
            System.out.println("新用户注册");
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo:   " + resultUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap userInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) userInfo.get("nickname");
//            String sex = (String) userInfo.get("sex");
            String headimgurl = (String) userInfo.get("headimgurl");

            ucenterMember = new UcenterMember();
            ucenterMember.setNickname(nickname);
//            ucenterMember.setSex(sex);
            ucenterMember.setOpenid(openid);
            ucenterMember.setAvatar(headimgurl);

            boolean save = ucenterMemberService.save(ucenterMember);

            if (!save){
                throw new GuliException(ResultCode.ERROR,"错误！");
            }

        }

        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        //存入cookie
        //CookieUtils.setCookie(request, response, "guli_jwt_token", token);

        //因为端口号不同存在蛞蝓问题，cookie不能跨域，所以这里使用url重写
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
