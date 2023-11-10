package com.mingguang.dreambuilder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mingguang.dreambuilder.entity.enums.Role;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@JsonIgnoreProperties(value = {"password","credentials"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String account;
    @JsonIgnore
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String wxId;
    private String pic;
    private String nickname;
    private Role role = Role.GUEST;
    @JsonIgnore
    Boolean enabled = false;
    @JsonIgnore
    Boolean accountNonExpired = false;
    @JsonIgnore
    Boolean accountNonLocked = false;
    @JsonIgnore
    Boolean credentialsNonExpired = false;
    @JsonIgnore
    String credentials = "";
}
