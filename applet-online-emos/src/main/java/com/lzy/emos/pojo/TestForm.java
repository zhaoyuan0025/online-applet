package com.lzy.emos.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: applet-online-emos
 * @description: 测试表单
 * @author: lzy
 * @create: 2022-06-16 21:08
 **/
@Data
public class TestForm implements Serializable {

    @NotBlank
    @ApiModelProperty("姓名")
    private String username;
}
