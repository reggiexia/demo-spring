package com.xhh.demo.sso.auth.build.config;

import com.xhh.demo.sso.auth.build.RoleService;
import com.xhh.demo.sso.auth.build.User;
import com.xhh.demo.sso.auth.build.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 配置用户处理
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/20 上午10:39
 */
@Log4j2
@Profile("build")
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) {
        log.debug("loadUserByUsername, s: {}", s);
        if (!StringUtils.hasText(s)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        User user = userService.queryByUserName(s).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        List<String> roles = roleService.queryByUserName(s).orElseThrow(() -> new UsernameNotFoundException("角色不存在"));
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        log.debug("loadUserByUsername, authorities: {}", authorities);

        return new org.springframework.security.core.userdetails.User(
                s, user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
