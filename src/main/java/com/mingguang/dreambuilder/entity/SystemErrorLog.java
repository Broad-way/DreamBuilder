package com.mingguang.dreambuilder.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class SystemErrorLog {
    Instant errorAt = Instant.now();
    String errorMsg = "";
    String errorStack = "";

    @Override
    public String toString() {
        return errorMsg+"\n"+errorStack;
    }
}
