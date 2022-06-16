package com.lzy.emos.enums;

import lombok.Getter;

/**
 * @program: mall
 * @description: 返回的枚举类
 * @author: lzy
 * @create: 2021-07-15 10:21
 **/
@Getter
public enum ResultEnum {
    /**
     * 返回结果枚举，每个枚举代表着一个返回状态
     */
    SUCCESS(20000, "操作成功！"),
    ERROR(40000, "操作失败！"),
    DATA_NOT_FOUND(40001, "查询失败！"),
    PARAMS_NULL(40002, "参数不能为空！"),
    PARAMS_ERROR(40005, "参数不合法！"),

    NOT_LOGIN(40003, "当前账号未登录！"),

    USER_EMPTY(5000,"用户信息错误"),
    USER_ALREADY_EXIST(5001,"用户已经存在，不能重复注册！"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
