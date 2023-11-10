package com.mingguang.dreambuilder.dto;

import lombok.Data;

@Data
public class WxLoginReq {
    String code;
    String rawData;
    String signature;
}
