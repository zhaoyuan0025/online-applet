package com.lzy.emos.exception;

import com.lzy.emos.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @program: applet-online-emos
 * @description: 自定义的异常处理类
 * @author: lzy
 * @create: 2022-06-16 02:12
 **/
@Data
public class CommonException extends RuntimeException implements Serializable {

    private Integer errorCode = ResultEnum.ERROR.getCode();
    private String message;

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public CommonException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public CommonException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public CommonException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public CommonException(ResultEnum responseEnum, Object... args){
        this.errorCode = responseEnum.getCode();
        this.message = MessageFormat.format(responseEnum.getMsg(),args);
    }

    public CommonException(ResultEnum responseEnum, Throwable cause, Object... args){
        super(MessageFormat.format(responseEnum.getMsg(),args),cause);
        this.errorCode = responseEnum.getCode();
        this.message = responseEnum.getMsg();
    }
}
