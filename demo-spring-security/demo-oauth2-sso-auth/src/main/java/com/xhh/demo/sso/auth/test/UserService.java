package com.xhh.demo.sso.auth.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/20 上午10:42
 */
@Log4j2
@Component
public class UserService {

    public Optional<User> queryByUserName(String userName) throws UsernameNotFoundException {
        log.info("queryByUserName: {}", userName);
        Map<String, User> userMap = queryAll().stream().collect(Collectors.toMap(User::getUserName, user -> user));
        return Optional.ofNullable(userMap.get(userName));
    }

    private List<User> queryAll() {
        List<User> users = new ArrayList<>();
        User l = new User();
        l.setUserName("lufei");
        l.setPassword("password");
        users.add(l);

        User z = new User();
        z.setUserName("zhuoluo");
        z.setPassword("password");
        users.add(z);
        return users;
    }
}
