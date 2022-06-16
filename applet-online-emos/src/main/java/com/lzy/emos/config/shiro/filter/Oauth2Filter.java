package com.lzy.emos.config.shiro.filter;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lzy.emos.config.shiro.JwtUtil;
import com.lzy.emos.config.shiro.Oauth2Token;
import com.lzy.emos.config.shiro.ThreadLocalToken;
import com.lzy.emos.pojo.LoginUserInfo;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: applet-online-emos
 * @description: 过滤器 过滤请求 bean要设置成多例的  否则threadLocal 使用会出错
 * @author: lzy
 * @create: 2022-06-17 00:05
 **/
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class Oauth2Filter extends AuthenticatingFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 拦截请求之后 将token封装成令牌对象 Oauth2Token
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = this.getRequestToken(httpServletRequest);
        if (ObjectUtil.isEmpty(token)){
            return null;
        }

        return new Oauth2Token(token);
    }

    /**
     * 标记那些请求需要被处理
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest =(HttpServletRequest) request;
        //如果是option
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            //放行不用做处理
            return true;
        }
        //否则需要经过shiro的过滤
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //设置响应的一些信息
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //跨域请求的参数
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        //先清空threadLocalToken的数据
        threadLocalToken.remove();
        String token = this.getRequestToken(req);
        //如果token为空 则标记是无效的请求
        if (ObjectUtil.isEmpty(token)){
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的请求，没有权限");
            return false;
        }

        //如果不为空  验证token的有效性
        try {
            jwtUtil.verifierToken(token);
        }catch (TokenExpiredException e){
            //如果token已经过期了 刷新token 先判断redis存储的token 是否还在
            if (redisTemplate.hasKey(token)){
                //存在 先删除
                redisTemplate.delete(token);
                //从旧的token中获取 用户信息
                LoginUserInfo userInfo = jwtUtil.getLoginUserInfo(token);
                //覆盖旧的token
                token = jwtUtil.createToken(userInfo);
                //保存到redis
                redisTemplate.opsForValue().set(token,userInfo,cacheExpire, TimeUnit.DAYS);
            }else {
                //否则响应用户 告知身份已经过期 需要重新登录
                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
                resp.getWriter().print("登录信息已经过期");
                return false;
            }
        }catch (JWTDecodeException e){
            //如果是伪造的token
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的请求，没有权限");
            return false;
        }
        boolean b = executeLogin(req, resp); // 这里会调用shiro的realm类来执行认证和授权
        return b;
    }

    /**
     * shiro认证失败执行发方法
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //设置响应的一些信息
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //跨域请求的参数
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try {
            //响应认证失败的信息
            resp.getWriter().print(e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    public String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if (ObjectUtil.isEmpty(token)){
            token = request.getParameter("token");
        }
        return token;
    }
}
