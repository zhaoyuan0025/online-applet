package com.lzy.emos.advice;


import com.lzy.emos.exception.CommonException;
import com.lzy.emos.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h2>全局异常捕获处理</h2>
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Result<String> handlerCommerceException(Exception ex
    ) {
        Result<String> result = new Result<>(
                -1, "business error"
        );
        log.error("commerce service has error: [{}]", ex.getMessage(), ex);
        if (ex instanceof MethodArgumentNotValidException){
            //如果异常信息是属于字段的异常信息  只需要提取相应的具体信息即可
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) ex;
            result.setMsg(validException.getBindingResult().getFieldError().getDefaultMessage());
            return result;
        }
        //如果是自定义的异常信息
        else if (ex instanceof CommonException){
            CommonException commonException = (CommonException) ex;
            result.setCode(commonException.getErrorCode());
            result.setMsg(commonException.getMessage());
            return result;
        }
        else if (ex instanceof UnauthorizedException){
            result.setCode(40001);
            result.setMsg("你不具备权限访问");
            return result;
        }

        return result;
    }
}
