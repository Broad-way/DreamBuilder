package com.mingguang.dreambuilder.service;

import com.mingguang.dreambuilder.dao.UserDao;
import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.entity.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(
            String username) throws UsernameNotFoundException {
        User user = null;
        if (username.isBlank()){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        if (username.contains("@")){
            //TODO find by email
        }
        if (user == null){
           user = userDao.findByAccountAndEnabledTrue(username);
           if (user == null){
               // TODO search by other method or else
           }
        }

        if (user == null){
            throw new UsernameNotFoundException("用户 "+username+" 不存在");
        }
        return new UserPrincipal(user);
    }
}
