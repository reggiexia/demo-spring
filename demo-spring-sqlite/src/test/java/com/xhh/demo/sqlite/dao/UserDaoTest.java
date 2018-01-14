package com.xhh.demo.sqlite.dao;

import com.xhh.demo.sqlite.BaseSpringTest;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * UserDao 单元测试
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2018/1/9 下午5:08
 */
@Log4j2
public class UserDaoTest extends BaseSpringTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User("tiger", "pass");
        assertTrue(userDao.insert(user));
    }

    @Test
    public void testSelect() {
        List<Map<String, Object>> list = userDao.select();
        assertTrue(list.size() > 0);
        log.debug(list);
    }

    @Test
    public void testDelete() {
        assertTrue(userDao.delete(2));
    }

    @Test
    public void testUpdate() {
        User user = new User(1,"fusu", "tiger");
        assertTrue(userDao.update(user));
    }
}
