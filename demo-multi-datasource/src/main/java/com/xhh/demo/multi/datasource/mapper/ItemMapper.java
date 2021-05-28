package com.xhh.demo.multi.datasource.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xhh.demo.multi.datasource.model.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库操作类
 *
 * @author tiger
 */
@Repository
public interface ItemMapper extends BaseMapper<Item> {

    List<Item> selectAll(@Param(Constants.WRAPPER) Wrapper<Item> wrapper);
}
