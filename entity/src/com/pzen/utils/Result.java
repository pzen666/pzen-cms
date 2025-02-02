package com.pzen.utils;

import java.util.Map;

public class Result<T> {

    private int code;
    private String message;
    private T data;

    // 常用错误码
    public static final int SUCCESS = 200; // 成功
    public static final int BAD_REQUEST = 400; // 请求错误
    public static final int UNAUTHORIZED = 401; // 未授权
    public static final int FORBIDDEN = 403; // 禁止访问
    public static final int NOT_FOUND = 404; // 未找到
    public static final int METHOD_NOT_ALLOWED = 405; // 方法不被允许
    public static final int CONFLICT = 409; // 冲突
    public static final int INTERNAL_SERVER_ERROR = 500; // 内部服务器错误
    public static final int BAD_GATEWAY = 502; // 网关错误
    public static final int SERVICE_UNAVAILABLE = 503; // 服务不可用
    public static final int GATEWAY_TIMEOUT = 504; // 网关超时


    // 构造函数
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态方法创建成功响应
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(SUCCESS, message, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, "success", data);
    }

    // 静态方法创建失败响应
    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> failure(int code) {
        return new Result<>(code, "fail", null);
    }

    public static Result<Map<String, String>> error(String msg) {
        return new Result<>(BAD_REQUEST, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
