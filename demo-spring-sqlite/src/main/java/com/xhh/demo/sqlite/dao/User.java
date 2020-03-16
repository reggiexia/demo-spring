package com.xhh.demo.sqlite.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户
 *
 * @author tiger
 * @version 1.0.0 createTime: 2018/1/9 下午4:51
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -913507797890156344L;

    private int id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
