package com.dbDesign.Jacky.service.intermediateService;

import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface StudentCourseService {
    ServiceResult saveStudentCourse(StudentCourse studentCourse);

    ServiceResult getStudentListByCourseId(Integer courseId);

    ServiceResult getCourseListByStudentId(Integer studentId);

    ServiceResult chooseCourse(StudentCourse studentCourse);

    ServiceResult remoteStudentCourse(Integer studentId, Integer courseId);

    ServiceResult remoteByStudentId(Integer studentId);

    ServiceResult remoteByCourseId(Integer courseId);
}
