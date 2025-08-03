package com.jack.asignment.general.data.query.util;

/**
 * Wrapper response.
 */
public class RetCode<T> {
    private String retCode;
    private String msg;
    private T t;

    @SuppressWarnings("unused")
    public RetCode() {
    }

    public RetCode(String retCode) {
        this(retCode, null, null);
    }


    public RetCode(String retCode, T t) {
        this(retCode, null, t);
    }

    public RetCode(String retCode, String msg) {
        this.msg = msg;
        this.retCode = retCode;
    }

    public RetCode(String retCode, String msg, T t) {
        this.msg = msg;
        this.retCode = retCode;
        this.t = t;
    }

    public static <T> RetCode<T> success() {
        return new RetCode<T>("200", "Success");
    }

    public static <T> RetCode<T> success(T t) {
        return new RetCode<T>("200", "Success", t);
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return t;
    }

    public void setResult(T t) {
        this.t = t;
    }
}
