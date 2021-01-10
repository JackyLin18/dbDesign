package com.dbDesign.Jacky.service.viewService;

import com.dbDesign.Jacky.model.vo.ServiceResult;

import java.util.List;

public interface GradeReportViewService {
    ServiceResult getAllGradeReport();

    ServiceResult getGradeReportByCourseId(Integer courseId);

    ServiceResult getGradeReportByCourseIdList(List<Integer> courseIdList);

    ServiceResult getGradeReportByCourseName(String courseName);

    ServiceResult getGradeReportByTeacherId(Integer teacherId);

    ServiceResult getGradeReportByTeacherName(String teacherName);

    ServiceResult getGradeReportByDepartmentId(Integer departmentId);
}
