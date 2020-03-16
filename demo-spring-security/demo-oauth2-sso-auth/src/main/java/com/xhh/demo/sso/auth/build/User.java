package com.xhh.demo.sso.auth.build;

import lombok.*;

/**
 * 用户信息
 *
 * @author tiger
 * @version 1.0.0 createTime: 2017/4/20 下午1:58
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userName;
    private String password;
}
