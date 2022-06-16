package com.lzy.emos.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: applet-online-emos
 * @description: shiro 实现用户的认证与授权
 * @author: lzy
 * @create: 2022-06-16 23:21
 **/
@Component
@Slf4j
public class Oauth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * 认证 登录时调用
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        //TODO 验证账户的有效性
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();

        return info;
    }

    /**
     * 授权 验证权限是调用
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //TODO 查询用户的权限列表
        //TODO 将权限列表添加到
        return info;
    }
}
