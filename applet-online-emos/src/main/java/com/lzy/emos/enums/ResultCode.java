package com.lzy.emos.enums;

import lombok.Getter;

/**
 * @program: wx_springboot
 * @description: 返回的枚举类
 * @author: lzy
 * @create: 2021-07-15 10:21
 **/
@Getter
public enum ResultCode {

    SUCCESS(2000,"操作成功！"),
    ERROR(5000,"操作失败")
    ;

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
