package com.mmall.common;

/**
 * @author Admin
 */
public enum ResponseCode {
    /**
     * SUCCESS
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * ERROR
     */
    ERROR(1, "ERROR"),
    /**
     * NEED_LOGIN
     */
    NEED_LOGIN(10, "NEED_LOGIN"),
    /**
     * ILLEGAL_ARGUMENT
     */
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
