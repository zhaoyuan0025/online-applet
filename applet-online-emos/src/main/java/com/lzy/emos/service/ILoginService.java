package com.lzy.emos.service;

/**
 * 登录的接口实现类
 */
public interface ILoginService {

    /**
     * 通过小程序传递下来的code换取openid
     * @param code
     * @return string
     */
    String getUserInfo(String code);
}
