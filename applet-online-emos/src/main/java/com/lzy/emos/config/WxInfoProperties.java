package com.lzy.emos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wx")
@Component
@Data
public class WxInfoProperties {

    /**
     * 小程序的appid
     */
    public String appId;

    /**
     * 小程序的appsecret
     */
    public String appSecret;
}
