package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.dto.ApiResponse;
import com.mingguang.dreambuilder.dto.LoginSuccessResp;
import com.mingguang.dreambuilder.dto.WxLoginReq;
import com.mingguang.dreambuilder.service.LoginService;
import com.mingguang.dreambuilder.util.ApiResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiOperation(value = "获取用户信息")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/api/login/wx")
    public ApiResponse<LoginSuccessResp> WxLogin(@RequestBody WxLoginReq wxLoginReq){
        return ApiResponseUtil.responseGenerator(()->{
            return loginService.wxLogin(wxLoginReq);
        });
    }
}
