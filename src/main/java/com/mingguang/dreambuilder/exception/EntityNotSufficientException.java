package com.mingguang.dreambuilder.exception;

public class EntityNotSufficientException extends IllegalArgumentException {
    public EntityNotSufficientException(String msg) {
        super(msg);
    }

    public EntityNotSufficientException() {
        this("实体数量不足");
    }
}
