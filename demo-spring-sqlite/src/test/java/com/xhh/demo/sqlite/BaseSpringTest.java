package com.xhh.demo.sqlite;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 单元测试父类
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2018/1/9 下午5:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseSpringTest extends TestCase {

}
