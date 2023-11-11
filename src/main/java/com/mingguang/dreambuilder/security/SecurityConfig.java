package com.mingguang.dreambuilder.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingguang.dreambuilder.dto.ApiResponse;
import com.mingguang.dreambuilder.dto.LoginSuccessResp;
import com.mingguang.dreambuilder.entity.UserPrincipal;
import com.mingguang.dreambuilder.service.UserDetailServiceImpl;
import com.mingguang.dreambuilder.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MyJwtTokenProvider myJwtTokenProvider;
    @Autowired
    private UserDetailServiceImpl userDetailsService;



    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // config ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        http.csrf().disable()//跨域检察关闭
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/doc.html").permitAll()
                            .requestMatchers(// 添加需要测试的接口路径，正式使用时需要删除
                                    new AntPathRequestMatcher("/login"),
                                    new AntPathRequestMatcher("/static/**"),
                                    new AntPathRequestMatcher("/js/**"),
                                    new AntPathRequestMatcher("/css/**"),
                                    new AntPathRequestMatcher("/api/login/**"),
                                    new AntPathRequestMatcher("/api/tasks/**"),
                                    new AntPathRequestMatcher("/api/recommend/**"),
                                    new AntPathRequestMatcher("/api/check/**")
                            ).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenFilter(myJwtTokenProvider),
                                 UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin ->{
                    formLogin.loginProcessingUrl("/api/login").successHandler((req,resp,auth)->{
                        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
                        if (Objects.equals(userPrincipal.getUser().getId(),
                                           Objects.requireNonNull(
                                                   Utils.getUser()).getId())){
                            resp.setContentType("application/json;charset=utf-8");
                            resp.setCharacterEncoding("UTF-8");

                            LoginSuccessResp loginSuccessResp=new LoginSuccessResp();
                            loginSuccessResp.setUserPrincipal(userPrincipal);
                            loginSuccessResp.setTOKEN(
                                    jwtTokenProvider().generateToken(userPrincipal));
                            ApiResponse<LoginSuccessResp> rsp = ApiResponse.ok(loginSuccessResp);
                            resp.getWriter().write(objectMapper.writeValueAsString(rsp));
                        }
                    }).failureHandler((req,resp,exp)->{
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        ApiResponse<String> rsp = ApiResponse.error(400,"登录失败，用户名或密码错误");
                        resp.getWriter().write(objectMapper.writeValueAsString(rsp));
                    }).permitAll();
                })
                .logout(logout ->{
                    logout.logoutUrl("/api/logout").logoutSuccessHandler(logoutSuccessHandler()).permitAll();
                })
                .exceptionHandling(exceptionHandling ->{
                    exceptionHandling.authenticationEntryPoint(((request, response, authException) -> {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        ApiResponse<String> resp = ApiResponse.error(403,
                                                                       "未登录");
                        response.getWriter().write(objectMapper.writeValueAsString(resp));
                    }));
                });
        return http.build();
    }

    @Primary
    @Bean
    AuthenticationManagerBuilder config(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public MyJwtTokenProvider jwtTokenProvider(){
        return new MyJwtTokenProvider();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);
    }
}
