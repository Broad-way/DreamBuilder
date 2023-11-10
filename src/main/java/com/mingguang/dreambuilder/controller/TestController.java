package com.mingguang.dreambuilder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
@Slf4j
public class TestController {
    @GetMapping("/hello")
    @ApiOperation(value = "测试方法")
    public String hello(){
        return "hello security!";
    }
}
