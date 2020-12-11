package com.dbDesign.Jacky.model.entity.intermediate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName StudentCourse
 * @Author Jacky
 * @Description
 **/
@Data
public class StudentCourse {
    // 学生id
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    // 课程id
    @TableId(type = IdType.INPUT)
    private Integer courseId;
    // 课程成绩
    private BigDecimal grade;
    // 创建时间
    @TableField(fill = FieldFill.INSERT,select = false)
    private Timestamp createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "StudentCourse{" +
                "studentId=" + studentId +
                ", courseId=" + courseId +
                ", grade=" + grade +
                '}';
    }
}
