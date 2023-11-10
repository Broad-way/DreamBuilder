package com.mingguang.dreambuilder.dto;

import com.mingguang.dreambuilder.entity.UserPrincipal;
import lombok.Data;

@Data
public class LoginSuccessResp {
    UserPrincipal userPrincipal;
    String TOKEN;
}
