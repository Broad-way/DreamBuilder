package com.mingguang.dreambuilder.controller;

import io.swagger.annotations.Api;
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
    public String hello(){
        return "hello security!";
    }
}
