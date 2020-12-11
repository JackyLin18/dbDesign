package com.dbDesign.Jacky.common.enums;

/**
 * @Author Jacky
 * @Description 错误码/错误信息枚举类
 **/
public enum CodeEnum {

    SUCCESS(1000, "成功"),
    FAIL(1001,"失败"),
    DATABASE_ERROR(1002,"数据库执行出错"),
    PARAMETER_VALUE_INVALID(1003,"参数的值不合法"),
    PARAMETER_MISSING(1004,"请求参数不全"),
    NULL_RESULT(1005,"返回值为null"),
    NULL_PARAM(1006,"参数为空"),

    WITHOUT_AUTHORITY(2000,"没有权限"),

    OTHER_ERROR(5000,"其他(未知)错误");

    private Integer code;   // 错误码
    private String message; //  错误信息

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
