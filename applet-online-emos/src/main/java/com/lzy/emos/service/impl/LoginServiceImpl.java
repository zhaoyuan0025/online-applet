package com.lzy.emos.service.impl;

import com.lzy.emos.config.WxInfoProperties;
import com.lzy.emos.exception.CommonException;
import com.lzy.emos.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxInfoProperties wxInfoProperties;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取openid
     * @param code
     * @return
     */
    @Override
    public String getUserInfo(String code) {

        //先从Redis获取接口的调用凭证 Redis拿不到 在调用微信接口获取
        String accessToken = (String) redisTemplate.opsForValue().get("access_token");
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = this.getAccessToken(wxInfoProperties.appId, wxInfoProperties.getAppSecret());
        }
        //构造请求的url
        String openIdUrl = "";

        //构造请求的请求头数据

        return null;
    }

    /**
     * 获取微信的的接口调用凭证
     * @param appId
     * @param appSecret
     * @return token
     */
    public String getAccessToken(String appId,String appSecret){

        //构造请求的url
        try {

        }catch (Exception e){
            //出现异常
            throw new CommonException(5000,e.getMessage());
        }
        String requestUrl = "GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;

        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        if (response.getStatusCode().equals(HttpStatus.SC_OK)){
            //成功后返回相对应的状态码 和数据
            //得到返回json字符串 获取接口的调用凭证
            String body = response.getBody();

            //序列化为对象

            //返回accesstoken

            // 存入Redis中  并且设置有效期为7200秒 下次如果过期了 再重新调用接口获取


        }



        return null;
    }
}
