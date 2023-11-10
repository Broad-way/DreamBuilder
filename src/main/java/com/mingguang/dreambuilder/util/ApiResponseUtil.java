package com.mingguang.dreambuilder.util;

import com.mingguang.dreambuilder.dto.ApiResponse;
import com.mingguang.dreambuilder.entity.SystemErrorLog;
import com.mingguang.dreambuilder.exception.EntityNotFoundException;
import com.mingguang.dreambuilder.exception.UserUnauthenticatedException;
import com.mingguang.dreambuilder.exception.UserUnauthorizedException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.time.Instant;
import java.util.logging.Logger;

public class ApiResponseUtil {

    public static <T> ApiResponse<T> responseGenerator(ThrowingSupplier<T> block) {
        return responseGenerator(null, block);
    }

    public static <T> ApiResponse<T> responseGenerator(SaveSystemErrorLog saveSystemErrorLog, ThrowingSupplier<T> block) {
        try {
            return ApiResponse.ok(block.get());
        } catch (Throwable e) {
            SystemErrorLog systemErrorLog = new SystemErrorLog();
            systemErrorLog.setErrorMsg(e.getMessage() != null ? e.getMessage() : "未知错误");
            systemErrorLog.setErrorStack(ExceptionUtils.getStackTrace(e));
            systemErrorLog.setErrorAt(Instant.now());

            Logger.getLogger("systemError").warning(systemErrorLog.toString());

            if (e instanceof UserUnauthenticatedException) {
                return ApiResponse.error(401, "用户未登录");
            } else if (e instanceof UserUnauthorizedException) {
                return ApiResponse.error(403, "用户权限不足");
            } else if (e instanceof EntityNotFoundException) {
                return ApiResponse.error(404, "实体不存在");
            } else {
                return ApiResponse.error(1, e.getMessage() != null ? e.getMessage() : "未知错误");
            }
        }
    }

    @FunctionalInterface
    public interface SaveSystemErrorLog {
        void save(SystemErrorLog systemErrorLog);
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }
}
