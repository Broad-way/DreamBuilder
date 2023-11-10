package com.mingguang.dreambuilder.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

enum LoginMethod{
    ACCOUNT,
    OTHER
}

@Data
public class UserPrincipal implements UserDetails {
    @Resource
    private User user;
    private LoginMethod loginMethod = LoginMethod.ACCOUNT;
    private String usedCredential = "";
    public UserPrincipal(){
        super();
    }
    public UserPrincipal(User user){
        this.user = user;
        this.usedCredential = user.credentials;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("User"));
        return roles;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        switch (loginMethod){
            case ACCOUNT -> {
                return user.getPassword();
            }
            default -> {
                return new BCryptPasswordEncoder().encode("");
            }
        }
    }

    @Override
    public String getUsername() {
        switch (loginMethod){
            case ACCOUNT -> {
                return user.getAccount();
            }
            case OTHER -> {
                return usedCredential;
            }
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return user.enabled;
    }
}
