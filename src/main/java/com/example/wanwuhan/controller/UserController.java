package com.example.wanwuhan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wanwuhan.common.GlobalResult;
import com.example.wanwuhan.common.WechatUtil;
import com.example.wanwuhan.pojo.Attractions;
import com.example.wanwuhan.pojo.Comments;
import com.example.wanwuhan.pojo.User;
import com.example.wanwuhan.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 微信用户登录详情
     */
    @PostMapping("/loginByWeixin")
    @ResponseBody
    public GlobalResult user_login(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "rawData", required = false) String rawData,
                                   @RequestParam(value = "signature", required = false) String signature,
                                   @RequestParam(value = "encrypteData", required = false) String encrypteData,
                                   @RequestParam(value = "iv", required = false) String iv) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return GlobalResult.build(500, "签名校验失败", null);
        }
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
        User user = JSON.parseObject(userService.findUserByOpenId(openid),User.class);
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (user == null) {
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");

            user = new User();
            user.setOpenId(openid);
            user.setSkey(skey);
            user.setCreateTime(new Date());
            user.setLastVisitTime(new Date());
            user.setSessionKey(sessionKey);
            user.setCity(city);
            user.setProvince(province);
            user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            user.setGender(Integer.parseInt(gender));
            user.setNickName(nickName);

            userService.addUser(user);
        } else {
            // 已存在，更新用户登录时间
            user.setLastVisitTime(new Date());
            // 重新设置会话skey
            user.setSkey(skey);
            userService.updateUserInfo(user);
        }
        //encrypteData比rowData多了appid和openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);
        //6. 把openid返回给小程序
        GlobalResult result = GlobalResult.build(200, null, openid);
        return result;
    }
    @RequestMapping(value = "/findCommentByUserOpenid", method = RequestMethod.POST)
    @ResponseBody
    public String findCommentByAttractionId(@RequestParam(value = "openid",required = true)String openid){
        User user = JSON.parseObject(userService.findUserByOpenId(openid),User.class);
        List<Comments> commentsListAttraction = new ArrayList<>();
        for(int i = 0;i<user.getCommentsList().size();i++){
            Comments comment = new Comments(user.getCommentsList().get(i).getCommentImages(),
                    user.getCommentsList().get(i).getCommentContent(),
                    user.getCommentsList().get(i).getCommentTime());
            commentsListAttraction.add(comment);
        }
        String jsonCommentByOpenid = JSON.toJSONStringWithDateFormat(commentsListAttraction,"yyyy-MM-dd HH:mm:ss");
        return jsonCommentByOpenid;
    }
}