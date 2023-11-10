package com.mingguang.dreambuilder.exception;

public class EntityNotFoundException extends IllegalArgumentException {
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException() {
        this("请求实体不存在");
    }
}
