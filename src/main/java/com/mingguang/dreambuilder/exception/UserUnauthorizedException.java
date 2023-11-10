package com.mingguang.dreambuilder.exception;

public class UserUnauthorizedException extends IllegalArgumentException {
    public UserUnauthorizedException(String msg) {
        super(msg);
    }

    public UserUnauthorizedException() {
        this("用户未授权");
    }
}
