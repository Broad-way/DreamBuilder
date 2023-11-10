package com.mingguang.dreambuilder.exception;

public class UserUnauthenticatedException extends IllegalStateException {
    public UserUnauthenticatedException() {
        super("用户未登录");
    }
}
