package com.lzy.emos.xss;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @program: applet-online-emos
 * @description: xss  filter过滤器
 * @author: lzy
 * @create: 2022-06-16 22:23
 **/
@WebFilter(urlPatterns = "/*") // 指定拦截的请求(所有请求
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XssHttpServletRequestwrapper xssHttpServletRequestwrapper = new XssHttpServletRequestwrapper(httpServletRequest);
        chain.doFilter(xssHttpServletRequestwrapper,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
