package com.mingguang.dreambuilder.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mingguang.dreambuilder.dao.UserDao;
import com.mingguang.dreambuilder.dto.LoginSuccessResp;
import com.mingguang.dreambuilder.dto.WxLoginReq;
import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.entity.UserPrincipal;
import com.mingguang.dreambuilder.entity.enums.Role;
import com.mingguang.dreambuilder.exception.UserUnauthorizedException;
import com.mingguang.dreambuilder.security.MyJwtTokenProvider;
import com.mingguang.dreambuilder.util.HttpClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.procedure.ParameterMisuseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LoginService {
    @Autowired
    private UserDao userDao;

    @Value("${wx.RequestUrl}")
    private String requestUrl;
    @Value("${wx.AppID}")
    private String appId;
    @Value("${wx.AppScret}")
    private String appScret;
    @Autowired
    private MyJwtTokenProvider jwtTokenProvider;
    public LoginSuccessResp wxLogin(WxLoginReq wxLoginReq) {
        JSONObject rawDataJson = JSON.parseObject(wxLoginReq.getRawData());
        if (wxLoginReq.getCode() == null){
            throw new ParameterMisuseException("缺少必要参数 code");
        }else if (wxLoginReq.getRawData() == null){
            throw new ParameterMisuseException("缺少必要参数 rawData");
        }else if (wxLoginReq.getSignature() == null){
            throw new ParameterMisuseException("缺少必要参数 signature");
        }
        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId(wxLoginReq.getCode());
        //获取向wx服务器拿到的openid
        String openId = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        String signatureReqed = DigestUtils.sha1Hex(wxLoginReq.getRawData()+sessionKey);
        if (!wxLoginReq.getSignature().equals(signatureReqed)){
            throw  new UserUnauthorizedException("签名校验失败");
        }
        User requsetUser = userDao.findUserByWxId(openId);
        if (requsetUser==null){
            requsetUser = new User();
            requsetUser.setWxId(openId);
            requsetUser.setPic(rawDataJson.getString("avatarUrl"));
            requsetUser.setNickname(rawDataJson.getString("nickName"));
            requsetUser.setRole(Role.GUEST);

            requsetUser = userDao.save(requsetUser);
        }

        UserPrincipal userPrincipal = new UserPrincipal(requsetUser);
        LoginSuccessResp loginSuccessResp=new LoginSuccessResp();
        loginSuccessResp.setUserPrincipal(userPrincipal);
        loginSuccessResp.setTOKEN(
                jwtTokenProvider.generateToken(userPrincipal));
        return loginSuccessResp;
    }
    public JSONObject getSessionKeyOrOpenId(String code){
        Map<String,String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid",appId);
        requestUrlParam.put("secret",appScret);
        requestUrlParam.put("js_code",code);
        requestUrlParam.put("grant_type","authorization_code");
        return JSON.parseObject(HttpClientUtil.doPost(requestUrl,requestUrlParam));
    }
}
