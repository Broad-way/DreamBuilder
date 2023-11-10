package com.mingguang.dreambuilder.util;

import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.entity.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class Utils {
    public static User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal){
            UserPrincipal userDetails = (UserPrincipal)authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }
}
