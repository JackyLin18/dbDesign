package com.dbDesign.Jacky.service.intermediateService;

import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface StudentCourseService {
    ServiceResult getCourseListByStudentId(Integer studentId);

    ServiceResult chooseCourse(StudentCourse studentCourse);

    ServiceResult saveGrade(StudentCourse studentCourse);

    ServiceResult remoteStudentCourse(Integer studentId, Integer courseId);

    ServiceResult getGrade(Integer studentId,Integer courseId);

    ServiceResult getGradesByCourseId(Integer courseId);

    ServiceResult getGradesByStudentId(Integer studentId);

    ServiceResult remoteByStudentId(Integer studentId);

    ServiceResult remoteByCourseId(Integer courseId);
}
