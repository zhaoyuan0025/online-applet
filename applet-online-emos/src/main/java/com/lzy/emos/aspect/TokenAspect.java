package com.lzy.emos.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.lzy.emos.config.shiro.ThreadLocalToken;
import com.lzy.emos.utils.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: applet-online-emos
 * @description: 通过切面将token响应返回对象中
 * @author: lzy
 * @create: 2022-06-17 01:12
 **/
@Component
@Aspect
public class TokenAspect {

    @Autowired
    private ThreadLocalToken threadLocalToken;

    /**
     * 定义切入点
     */
    @Pointcut("execution(public * com.lzy.emos.controller.*.*(..)))")
    public void aspect(){
    }

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Result result = (Result) joinPoint.proceed(); //方法返回的结果进行强转
        String token = threadLocalToken.getToken();
        if (ObjectUtil.isNotEmpty(token)){
            result.setToken(token);
            threadLocalToken.remove();
        }
        return result;

    }
}
