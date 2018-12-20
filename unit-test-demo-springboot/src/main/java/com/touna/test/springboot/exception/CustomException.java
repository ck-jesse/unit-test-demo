package com.touna.test.springboot.exception;

/**
 * 自定义异常
 *
 * @Author chenck
 * @Date 2018/12/13 16:20
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
