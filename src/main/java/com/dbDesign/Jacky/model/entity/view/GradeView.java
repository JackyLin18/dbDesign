package com.dbDesign.Jacky.model.entity.view;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName GradeView
 * @Author Jacky
 * @Description
 **/
@Data
public class GradeView {
    private Integer studentId;
    private String studentName;
    private Integer courseId;
    private String courseName;
    private BigDecimal credit;
    private String classTime;
    private Integer courseHours;
    private String classAddress;
    private Timestamp examTime;
    private Integer teacherId;
    private String teacherName;
    private Integer departmentId;
    private BigDecimal usualGrade;
    private BigDecimal examGrade;
    private BigDecimal totalGrade;

    @Override
    public String toString() {
        return "GradeView{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", classTime='" + classTime + '\'' +
                ", courseHours=" + courseHours +
                ", classAddress='" + classAddress + '\'' +
                ", examTime=" + examTime +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", departmentId=" + departmentId +
                ", usualGrade=" + usualGrade +
                ", examGrade=" + examGrade +
                ", totalGrade=" + totalGrade +
                '}';
    }
}