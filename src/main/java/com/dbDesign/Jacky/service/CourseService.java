package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface CourseService {
    ServiceResult saveCourse(Course course);

    ServiceResult getCourseByCourseId(Integer courseId);

    ServiceResult getCourseListByTeacherId(Integer teacherId);

    ServiceResult getCourseListByOption(Course course,Integer courseHoursType,Integer creditType);

    ServiceResult getAllCourseList();

    ServiceResult remoteCourseByCourseId(Integer courseId);
}
