package com.lzy.emos.controller;

import com.lzy.emos.pojo.TestForm;
import com.lzy.emos.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    @PostMapping("/test")
    public Result<String> test(@Valid @RequestBody TestForm form){
        System.out.println("测试成功！！！");
        return new Result<>("响应成功！！！", form.getUsername());
    }

}
