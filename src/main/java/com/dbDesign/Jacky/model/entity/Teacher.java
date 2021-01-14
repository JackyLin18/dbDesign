package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @ClassName Teacher
 * @Author Jacky
 * @Description
 **/
@Data
@TableName("teacher_5062")
public class Teacher {
    // 教职工号
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 登录密码
    @TableField(select = false)
    private String password;
    // 姓名
    private String name;
    // 性别
    private Integer sex;
    // 出生年月
    private Date birthday;
    // 所在系号
    private Integer departmentId;
    // 职称
    private String title;
    // 专业
    private String major;
    // 教学方向
    private String teachingDirection;
    // 创建时间
    @TableField(fill = FieldFill.INSERT,select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", departmentId=" + departmentId +
                ", title='" + title + '\'' +
                ", major='" + major + '\'' +
                ", teachingDirection='" + teachingDirection + '\'' +
                '}';
    }
}
