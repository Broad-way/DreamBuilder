package com.mingguang.dreambuilder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@Slf4j
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        log.info("准备生成接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("明光筑梦志愿者端接口文档")
                .version("1.0")
                .description("明光筑梦志愿者端接口文档")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("志愿者端")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mingguang.dreambuilder.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}