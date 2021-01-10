package com.dbDesign.Jacky.model.entity.view;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName ChooseCourseView
 * @Author Jacky
 * @Description
 **/
@Data
public class ChooseCourseView {
    // 课程号
    private Integer courseId;
    // 学生学号
    private Integer studentId;
    // 学生姓名
    private String studentName;
    // 学生性别
    private Integer sex;
    // 平时成绩
    private BigDecimal usualGrade;
    // 考试成绩
    private BigDecimal examGrade;
    // 总评成绩
    private BigDecimal totalGrade;

    @Override
    public String toString() {
        return "ChooseCourseView{" +
                "courseId=" + courseId +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", sex=" + sex +
                ", usualGrade=" + usualGrade +
                ", examGrade=" + examGrade +
                ", totalGrade=" + totalGrade +
                '}';
    }
}
