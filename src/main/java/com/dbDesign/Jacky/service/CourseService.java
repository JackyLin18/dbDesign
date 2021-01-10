package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.vo.ServiceResult;

import java.util.List;

public interface CourseService {
    ServiceResult saveCourse(Course course);

    ServiceResult getCourseByCourseId(Integer courseId);

    ServiceResult getCourseByCourseName(String courseName);

    ServiceResult getCourseListByTeacherId(Integer teacherId);

    ServiceResult getCourseListByTeacherName(String teacherName);

    ServiceResult getCourseListByDepartmentId(Integer departmentId);

    ServiceResult getCourseListByOption(Course course, List<Integer> teacherIdList,
                                        Integer courseHoursType, Integer creditType);

    ServiceResult getAllCourseList();

    ServiceResult remoteCourseByCourseId(Integer courseId);
}
