package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @ClassName Student
 * @Author Jacky
 * @Description
 **/
@Data
@TableName("student_5062")
public class Student {
    // 学号
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 登录密码
    @TableField(select = false)
    private String password;
    // 姓名
    private String name;
    // 性别
    private Integer sex;
    // 出生日期
    private Date birthday;
    // 入学成绩
    private Integer enrolledScore;
    // 所在系别号
    private Integer departmentId;
    // 创建时间
    @TableField(fill = FieldFill.INSERT,select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", enrolledScore=" + enrolledScore +
                ", departmentId=" + departmentId +
                '}';
    }
}
