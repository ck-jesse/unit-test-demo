package com.touna.test.springboot;

import java.io.Serializable;

/**
 * @Author chenck
 * @Date 2018/12/11 16:57
 */
public class Result<T> implements Serializable {
    public static final int RESULT_STATUS_SUCCESS = 200;
    public static final int RESULT_STATUS_ERROR = 500;
    private int status = 200;
    private String message;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result<T> error(String message) {
        this.status = 500;
        this.message = message;
        this.data = null;
        return this;
    }

    public boolean isSuccess() {
        return this.status == 200;
    }

    public Result<T> success(T data) {
        this.status = 200;
        this.message = null;
        this.data = data;
        return this;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
