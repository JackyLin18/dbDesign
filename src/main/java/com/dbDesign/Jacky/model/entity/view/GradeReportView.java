package com.dbDesign.Jacky.model.entity.view;

import lombok.Data;

/**
 * @ClassName GradeReportView
 * @Author Jacky
 * @Description
 **/
@Data
public class GradeReportView {
    // 课程号
    private Integer courseId;
    // 选修总人数
    private Integer count;
    // 不及格人数
    private Integer fail;
    // 不及格比例
    private String failPre;
    // 及格人数
    private Integer pass;
    // 及格比例
    private String passPre;
    // 中等人数
    private Integer medium;
    // 中等比例
    private String mediumPre;
    // 良好人数
    private Integer good;
    // 良好比例
    private String goodPre;
    // 优秀人数
    private Integer excellent;
    // 优秀比例
    private String excellentPre;

    @Override
    public String toString() {
        return "GradeReportView{" +
                "course_id=" + courseId +
                ", count=" + count +
                ", fail=" + fail +
                ", failPre=" + failPre +
                ", pass=" + pass +
                ", passPre=" + passPre +
                ", medium=" + medium +
                ", mediumPre=" + mediumPre +
                ", good=" + good +
                ", goodPre=" + goodPre +
                ", excellent=" + excellent +
                ", excellentPre=" + excellentPre +
                '}';
    }
}
