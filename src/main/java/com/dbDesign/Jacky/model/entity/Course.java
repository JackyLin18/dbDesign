package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ClassName Course
 * @Author Jacky
 * @Description
 **/
@Data
@TableName("course_5062")
public class Course {
    // 课程号
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 课程名称
    private String name;
    // 任教教师工号
    private Integer teacherId;
    // 学时
    private Integer courseHours;
    // 学分
    private BigDecimal credit;
    // 上课时间
    private String classTime;
    // 上课地点
    private String classAddress;
    // 考试时间
    private Timestamp examTime;
    // 创建时间
    @TableField(fill = FieldFill.INSERT, select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Timestamp updateTime;

    public Date getExamTimeDate(){
        return new Date(examTime.getTime());
    }

    public Time getExamTimeDateTime(){
        return new Time(examTime.getTime());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                ", courseHours=" + courseHours +
                ", credit=" + credit +
                ", classTime='" + classTime + '\'' +
                ", classAddress='" + classAddress + '\'' +
                ", examTime=" + examTime +
                '}';
    }
}
