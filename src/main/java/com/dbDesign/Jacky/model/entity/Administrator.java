package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Administrator
 * @Author Jacky
 * @Description
 **/
@Data
public class Administrator {
    // 教职工号
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 登录密码
    @TableField(select = false)
    private String password;
    // 创建时间
    @TableField(fill = FieldFill.INSERT,select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private Timestamp updateTime;

    public Administrator(){}

    public Administrator(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
