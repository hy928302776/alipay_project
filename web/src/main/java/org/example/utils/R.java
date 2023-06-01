package org.example.utils;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;


public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;

    private boolean success;
    private T data;
    private String msg;

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = 200 == code;
    }


    public static boolean isSuccess(@Nullable R<?> result) {
        return (Boolean)Optional.ofNullable(result).map((x) -> {
            return ObjectUtil.equal(200, x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !isSuccess(result);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    public static <T> R<T> data(int code, T data, String msg) {
        return new R(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> R<T> success() {
        return new R(200, "操作成功","操作成功");
    }
    public static <T> R<T> success(String msg) {
        return new R(200, msg,"操作成功");
    }

    public static <T> R<T> fail(String msg) {
        return new R(500, msg,"系统异常");
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R(code, (Object)null, msg);
    }


    public static <T> R<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }

    public R() {
    }
}
