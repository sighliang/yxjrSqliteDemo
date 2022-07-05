package com.yxjr.yxjrDevOps.shiro;

import com.yxjr.yxjrDevOps.dao.UserDao;
import com.yxjr.yxjrDevOps.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userDao.getRole(username);
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }


    /**
     * 获取授权信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = "";
        if (token.getPassword() != null)
        {
            password = new String(token.getPassword());
        }
        User user;
        try {
            if(userDao.getUserName(userName)!=null){
                user = userDao.login(userName, password);
                if(user==null){
                    log.info("对用户[" + userName + "]进行登录验证..密码验证未通过{}");
                    throw  new IncorrectCredentialsException();
                }
            }else{
               return null;
            }
        }
        catch (Exception e)
        {
            log.info("对用户[" + userName + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
