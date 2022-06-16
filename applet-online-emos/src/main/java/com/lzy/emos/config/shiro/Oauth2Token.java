package com.lzy.emos.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: applet-online-emos
 * @description: 令牌的封装token对象
 * @author: lzy
 * @create: 2022-06-16 23:18
 **/
public class Oauth2Token implements AuthenticationToken {

    /**
     * token
     */
    private String token;

    public Oauth2Token(String token){
        this.token  = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
