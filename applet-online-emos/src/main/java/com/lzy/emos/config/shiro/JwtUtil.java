package com.lzy.emos.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.lzy.emos.exception.CommonException;
import com.lzy.emos.pojo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: applet-online-emos
 * @description: JWT 的工具类
 * @author: lzy
 * @create: 2022-06-16 22:46
 **/
@Slf4j
@Component
public class JwtUtil {

    /**
     * 密钥
     */
    @Value("${emos.jwt.secret}")
    private String secret;

    /**
     * 过期时间
     */
    @Value("${emos.jwt.expire}")
    private int expire;

    /**
     * 生成token
     * @param userInfo
     * @return
     */
    public String createToken(LoginUserInfo userInfo){
        //计算过期时间 通过dateutil工具类向后偏移5天
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,5);

        //加密的算法
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create().withClaim("login-user", JSON.toJSONString(userInfo))
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }

    /**
     * 获取用户信息 解析token
     * @param token
     * @return
     */
    public LoginUserInfo getLoginUserInfo(String token){
        DecodedJWT decode = JWT.decode(token);
        String userInfo = decode.getClaim("login-user").asString();
        //这里可以做一下非空的判断
        if (StringUtils.isEmpty(userInfo)){
            throw new CommonException(50001,"用户信息异常,请联系管理员");
        }
        log.info("login user info >>>>>>>>>>>>>>>>>>>>>>>>>>>[{}]",userInfo);
        LoginUserInfo loginUserInfo = JSON.parseObject(userInfo, LoginUserInfo.class);
        return loginUserInfo;
    }

    /**
     * 验证token的有效性
     * @param token
     */
    public void verifierToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }



}
