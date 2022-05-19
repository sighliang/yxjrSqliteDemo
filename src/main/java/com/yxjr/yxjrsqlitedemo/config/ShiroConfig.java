package com.yxjr.yxjrsqlitedemo.config;

import com.yxjr.yxjrsqlitedemo.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/toLogin");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //拦截接口
        filterChainDefinitionMap.put("/user/**", "user");  //存在用户权限
        filterChainDefinitionMap.put("/deviceInfo/**", "user");
        filterChainDefinitionMap.put("/logPackage/**", "anon");
        filterChainDefinitionMap.put("/taskInfo/**", "anon");
        filterChainDefinitionMap.put("/uploadPackage/**", "user");
        filterChainDefinitionMap.put("/log", "user");
        filterChainDefinitionMap.put("/device", "user");
        filterChainDefinitionMap.put("/toUser", "user");
        filterChainDefinitionMap.put("/main", "user");
        filterChainDefinitionMap.put("/logout", "user");
        filterChainDefinitionMap.put("/uPackage", "user");
        filterChainDefinitionMap.put("/task", "user");
        //开放接口
        filterChainDefinitionMap.put("/common/**","anon"); //开放权限
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
