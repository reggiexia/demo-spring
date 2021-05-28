package com.xhh.demo.multi.datasource.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据库对象
 *
 * @author tiger
 */
@Data
@TableName("Item")
public class Item {

    private Long id;
    private Long namespaceId;
    private String key;
    private String value;
    private String comment;
}
