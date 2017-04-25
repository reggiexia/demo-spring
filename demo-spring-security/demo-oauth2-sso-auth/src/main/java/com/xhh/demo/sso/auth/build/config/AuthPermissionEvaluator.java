package com.xhh.demo.sso.auth.build.config;

import com.xhh.demo.sso.auth.build.RoleService;
import com.xhh.demo.sso.auth.build.User;
import com.xhh.demo.sso.auth.build.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 配置许可处理
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/20 下午7:08
 */
@Profile("build")
@Component
public class AuthPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String username = authentication.getName();
        User user = userService.queryByUserName(username).orElse(new User());
        return roleService.authorized(user.getUserName(), targetDomainObject.toString(), permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
