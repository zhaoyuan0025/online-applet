package com.lzy.emos.config.shiro;

import com.lzy.emos.config.shiro.filter.Oauth2Filter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: applet-online-emos
 * @description: 将Oauth2Filter 和Oauth2Realm 整合到shiro中使其生效
 * @author: lzy
 * @create: 2022-06-17 00:50
 **/
@Configuration
@SuppressWarnings("all")
public class ShiroConfig {

    /**
     * 配置 SecurityManager
     * @param oauth2Realm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(Oauth2Realm oauth2Realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oauth2Realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         Oauth2Filter oauth2Filter){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //过滤请求
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2",oauth2Filter);
        shiroFilterFactoryBean.setFilters(filters);

        //哪些请求不需要被拦截
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/webjars/**","anon");
        map.put("/druid/**","anon");
        map.put("/app/**","anon");
        map.put("/swagger/**","anon");
        map.put("/test/**","anon");
        map.put("/swagger-ui.html","anon");
        map.put("/swagger-resources/**","anon");
        map.put("/captcha.jpg","anon");
        map.put("/v2/api-docs","anon");
//        map.put("","anon");

        map.put("/**","oauth2"); //其他所有请求都要拦截

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager
    ){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
