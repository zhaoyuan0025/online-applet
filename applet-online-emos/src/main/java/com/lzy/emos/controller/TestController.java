package com.lzy.emos.controller;

import com.lzy.emos.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: applet-online-emos
 * @description: 测试
 * @author: lzy
 * @create: 2022-06-16 13:11
 **/
@Api(tags = "测试接口")
@RestController
public class TestController {

    @ApiOperation("测试")
    @GetMapping("/test")
    public Result<String> test(){
        System.out.println("测试成功！！！");
        return new Result<>("响应成功！！！");
    }

}
