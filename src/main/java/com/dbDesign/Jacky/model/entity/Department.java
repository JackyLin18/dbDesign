package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Department
 * @Author Jacky
 * @Description
 **/
@Data
public class Department {
    // 系号
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 系名称
    private String name;
    // 系介绍
    private String introduction;
    // 创建时间
    @TableField(fill = FieldFill.INSERT,select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private Timestamp updateTime;
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    public Department(){

    }

    public Department(String name,String introduction){
        this.name = name;
        this.introduction = introduction;
    }

    public Department(Integer id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
