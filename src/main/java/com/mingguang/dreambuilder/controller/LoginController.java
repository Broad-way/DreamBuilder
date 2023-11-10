package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.dto.ApiResponse;
import com.mingguang.dreambuilder.dto.LoginSuccessResp;
import com.mingguang.dreambuilder.dto.WxLoginReq;
import com.mingguang.dreambuilder.service.LoginService;
import com.mingguang.dreambuilder.util.ApiResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录控制类")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login/wx")
    @ApiOperation(value = "微信登录")
    public ApiResponse<LoginSuccessResp> WxLogin(@ApiParam(name = "WxLoginReq", value = "登录请求所需数据，包括code,rawdata,signature")
                                                     @RequestBody WxLoginReq wxLoginReq){
        return ApiResponseUtil.responseGenerator(()->{
            return loginService.wxLogin(wxLoginReq);
        });
    }
}
