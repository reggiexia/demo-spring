package com.xhh.demo.sso.auth.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 角色
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/4/20 下午4:30
 */
@Log4j2
@Component
public class RoleService {

    public Optional<List<String>> queryByUserName(String userName) {
        return Optional.ofNullable(queryAll().get(userName));
    }

    public boolean authorized(String u, String a, String b) {
        log.debug("authorized, u: {}, a: {}, b: {}", u, a, b);
        return true;
    }

    private Map<String, List<String>> queryAll() {
        Map<String, List<String>> roleMap = new HashMap<>();
        List<String> lRole = new ArrayList<>();
        lRole.add("admin");
        lRole.add("read");
        lRole.add("write");
        roleMap.put("lufei", lRole);

        List<String> zRole = new ArrayList<>();
        zRole.add("read");
        zRole.add("write");
        roleMap.put("zhuoluo", zRole);
        return roleMap;
    }
}
