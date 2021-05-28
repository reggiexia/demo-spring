package com.xhh.demo.multi.datasource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhh.demo.multi.datasource.mapper.ItemMapper;
import com.xhh.demo.multi.datasource.model.Item;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Item 表操作接口
 *
 * @author tiger
 */
@Log4j2
@RestController
public class ItemController {

    @Resource
    private ItemMapper itemMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/items")
    public List<Item> listAll() {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        return itemMapper.selectAll(queryWrapper.in("id", 1L, 2L, 3L));
    }

    @GetMapping("/event/{name}")
    public String sendEvent(@PathVariable("name") String name) {
        Set<String> keys = new HashSet<>();
        keys.add(name);
        try {
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
        } catch (Exception e) {
            log.error("publishEvent error: ", e);
        }
        return "success";
    }

}
