package com.cgsx.parking_system.util;

public enum ErrorEnum {
    // 数据操作错误定义
    SUCCESS(200, "成功"),

    USER_ACCOUNT_NOT_EXIST(400, "账号不存在！"),
    USER_CREDENTIALS_ERROR(401, "密码错误！"),
    NO_AUTH(402,"你未登录，请登录先！"),
    NO_PERMISSION(403,"没有权限！"),
    USER_ACCOOUT_ELSE(405, "您的账号在异地登录,可能由于密码泄露，建议修改密码"),
    CAR_EXIST(406, "车牌号已存在"),
    CAR_OWNER_ERROR(407, "车牌号与车主不符合"),
    SPACE_FULL_ERROR(408, "没有剩余车位！"),
    SPACE_CAR_EXIST(409, "该车位停有车，无法下线！"),
    LICENSE_ERROR(410, "识别错误"),

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