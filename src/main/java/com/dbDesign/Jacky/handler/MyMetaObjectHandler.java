package com.dbDesign.Jacky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @ClassName MyMetaObjectHandler
 * @Author Jacky
 * @Description
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * @Author Jacky
     * @Description 添加数据时的自动填充功能
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill...");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (metaObject.hasSetter("createTime") && metaObject.hasSetter("updateTime")) {
            this.setFieldValByName("createTime",
                    new Timestamp(System.currentTimeMillis()), metaObject);
            this.setFieldValByName("updateTime",
                    timestamp, metaObject);
        }
    }

    /**
     * @Author Jacky
     * @Description 更新数据时的自动填充功能
     **/
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill...");
        this.setFieldValByName("updateTime", new Timestamp(System.currentTimeMillis()), metaObject);
    }
}
