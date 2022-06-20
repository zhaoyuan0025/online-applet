package com.lzy.emos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * @program: applet-online-emos
 * @description: bean对象
 * @author: lzy
 * @create: 2022-06-17 00:16
 **/
@Configuration
public class BeanConfig {

//    @Bean
//    public RedisTemplate redisTemplate(){
//        return new RedisTemplate();
//    }

    @Bean("restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
