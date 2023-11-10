package com.mingguang.dreambuilder.exception;

public class UserBlockedException extends IllegalArgumentException {
    public UserBlockedException(String msg) {
        super(msg);
    }

    public UserBlockedException() {
        this("用户已禁用");
    }
}
