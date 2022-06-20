package com.lzy.emos.service.impl;

import com.lzy.emos.config.WxInfoProperties;
import com.lzy.emos.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxInfoProperties wxInfoProperties;


    @Override
    public String getUserInfo(String code) {
        return null;
    }

    /**
     * 获取微信的的接口调用凭证
     * @param appId
     * @param appSecret
     * @return token
     */
    public String getAccessToken(String appId,String appSecret){

//        restTemplate.getForEntity()
        return null;
    }
}
