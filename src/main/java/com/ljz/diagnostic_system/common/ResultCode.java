package com.ljz.diagnostic_system.common;

public enum ResultCode implements IErrorCode {
    SUCCESS(200,"操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String massage;

    ResultCode(long code, String massage) {
        this.code = code;
        this.massage = massage;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return massage;
    }
}
