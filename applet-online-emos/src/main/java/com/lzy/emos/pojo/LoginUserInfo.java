package com.lzy.emos.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: applet-online-emos
 * @description: 登录用户的 信息
 * @author: lzy
 * @create: 2022-06-16 22:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserInfo implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String username;
}
