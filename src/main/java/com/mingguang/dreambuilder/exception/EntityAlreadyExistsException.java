package com.mingguang.dreambuilder.exception;

public class EntityAlreadyExistsException extends IllegalArgumentException {
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }

    public EntityAlreadyExistsException() {
        this("请求实体已存在");
    }
}
