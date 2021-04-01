package com.cgsx.parking_system.util;

public enum ErrorEnum {
    // 数据操作错误定义
    SUCCESS(200, "成功"),

    USER_ACCOUNT_NOT_EXIST(400, "账号不存在！"),
    USER_CREDENTIALS_ERROR(401, "密码错误！"),
    NO_AUTH(402,"你未登录，请登录先！"),
    NO_PERMISSION(403,"没有权限！"),
    USER_ACCOOUT_ELSE(405, "您的账号在异地登录,可能由于密码泄露，建议修改密码"),

    NOT_FOUND(404, "未找到该资源!"),

    INTERNAL_SERVER_ERROR(500, "服务器跑路了"),
    PARAM_IS_BLANK(501, "参数为空")
    ;

    /** 错误码 */
    private Integer errorCode;

    /** 错误信息 */
    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}