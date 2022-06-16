package com.lzy.emos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: applet-online-emos
 * @description: 启动类
 * @author: lzy
 * @create: 2022-06-16 13:09
 **/
@SpringBootApplication
public class OnlineEmosApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineEmosApplication.class,args);
        System.out.println("环境启动成功！！！！");
    }
}
