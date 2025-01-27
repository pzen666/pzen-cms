package com.pzen;

public class Result {

    // 成功和失败的状态码常量
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = 400;

    public String code;
    public Object data;
    public String msg;

    public Result(Integer errorCode, Object data, String msg) {
    }

    // 静态方法创建成功结果
    public static Result success(Object data, String msg) {
        return new Result(SUCCESS_CODE, data, msg);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, data, "获取数据成功!");
    }

    // 静态方法创建失败结果
    public static Result failure(Object data, String msg) {
        return new Result(ERROR_CODE, data, msg);
    }

    public static Result failure(Object data) {
        return new Result(ERROR_CODE, data, "操作数据失败!");
    }
    public static Result error(Object data, String msg) {
        return new Result(ERROR_CODE, data, msg);
    }

    public static Result error(Object data) {
        return new Result(ERROR_CODE, data, "获取数据失败!");
    }




}
