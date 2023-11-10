package com.mingguang.dreambuilder.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "ok", data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}