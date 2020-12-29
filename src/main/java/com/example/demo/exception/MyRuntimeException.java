package com.example.demo.exception;

/**
 * @Author wangnan
 * @Date 2019/2/22/022 2019-02 13:57
 * @Param []
 * @return
 **/
public class MyRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6925278824391495117L;

    public MyRuntimeException(String message) {
        super(message);
    }

}