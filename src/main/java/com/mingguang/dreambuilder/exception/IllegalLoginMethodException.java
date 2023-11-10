package com.mingguang.dreambuilder.exception;

public class IllegalLoginMethodException extends IllegalArgumentException {
    public IllegalLoginMethodException(String msg) {
        super(msg);
    }

    public IllegalLoginMethodException() {
        this("非法的登录方式");
    }
}

