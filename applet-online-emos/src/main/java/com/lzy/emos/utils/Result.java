package com.lzy.emos.utils;

import com.lzy.emos.enums.ResultCode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: wx_springboot
 * @description: 返回的结果集
 * @author: lzy
 * @create: 2021-07-15 10:24
 **/
@Data
public class Result<T> implements Serializable {

    /**
     * 返回的状态码
     */
    private Integer code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * token
     */
    private String token;

    /**
     * 数据
     */
    private T data;

    /**
     * 接口的调用时间
     */
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 默认是成功
     */
    public Result(){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.date = new Date();
    }

    public Result(String msg){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = msg;
    }

    public Result(T data){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    public Result(String msg, T data){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
        this.date = new Date();
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
