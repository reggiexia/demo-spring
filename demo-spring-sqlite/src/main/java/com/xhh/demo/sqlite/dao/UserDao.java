package com.xhh.demo.sqlite.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 用户 Dao
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2018/1/9 下午4:54
 */
@Log4j2
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void create() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
                "name, password)");
    }

    public boolean insert(User user) {
        int result = jdbcTemplate.update("INSERT INTO user(name, password) VALUES (?, ?)", user.getName(),
                user.getPassword());
        return result > 0;
    }

    public List<Map<String, Object>> select() {
        return jdbcTemplate.queryForList("SELECT id, name, password FROM user");
    }

    public boolean delete(int id) {
        int result = jdbcTemplate.update("DELETE FROM user WHERE id = ?", id);
        return result > 0;
    }

    public boolean update(User user) {
        int result = jdbcTemplate.update("UPDATE user SET name = ?, password = ? WHERE id = ?", user.getName(),
                user.getPassword(), user.getId());
        return result > 0;
    }
}
