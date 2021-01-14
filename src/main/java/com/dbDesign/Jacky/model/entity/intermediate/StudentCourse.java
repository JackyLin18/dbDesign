package com.dbDesign.Jacky.model.entity.intermediate;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName StudentCourse
 * @Author Jacky
 * @Description
 **/
@Data
@TableName("student_course_5062")
public class StudentCourse {
    // 学生id
    @TableId(type = IdType.INPUT)
    private Integer studentId;
    // 课程id
    @TableId(type = IdType.INPUT)
    private Integer courseId;
    // 平时成绩
    private BigDecimal usualGrade;
    // 考试成绩
    private BigDecimal examGrade;
    // 总评成绩
    private BigDecimal totalGrade;
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
                ", usualGrade=" + usualGrade +
                ", examGrade=" + examGrade +
                ", totalGrade=" + totalGrade +
                '}';
    }
}
