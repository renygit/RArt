package com.reny.git.rart.core;

/**
 * Created by admin on 2019/10/24.
 */

public class ResultException extends RuntimeException {

    public static final int SUCCESSCODE = 0;
    public static final int LOGIN_OVERDUE = 5;
    public static final int SERVICE_ERROR = 11;
    public static final int COMMANDNAME_ERROR = 6;
    //public static final int XJGY_ERROR = 317;//在执行下架推广的供应  会提示此错误码
    public static final int UNKNOWNERROR = 9999;//自定义错误码

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
