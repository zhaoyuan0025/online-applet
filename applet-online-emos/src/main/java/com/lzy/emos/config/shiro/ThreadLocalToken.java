package com.lzy.emos.config.shiro;

import org.springframework.stereotype.Component;

/**
 * @program: applet-online-emos
 * @description: 存储共享token
 * @author: lzy
 * @create: 2022-06-16 23:43
 **/
@Component
public class ThreadLocalToken {

    private ThreadLocal<String> threadLocal = new ThreadLocal();

    public void setToken(String token){
        threadLocal.set(token);
    }

    public String getToken(){
        return threadLocal.get();
    }

    public void remove(){
        threadLocal.remove();
    }

}
