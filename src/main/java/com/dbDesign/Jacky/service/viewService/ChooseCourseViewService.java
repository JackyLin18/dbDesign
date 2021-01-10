package com.dbDesign.Jacky.service.viewService;

import com.dbDesign.Jacky.model.vo.ServiceResult;

import java.util.List;

public interface ChooseCourseViewService {
    ServiceResult getAllChooseCourse();

    ServiceResult getChooseCourseByCourseId(Integer courseId);

    ServiceResult getChooseCourseByCourseIdList(List<Integer> courseIdList);

    ServiceResult getChooseCourseByCourseName(String courseName);

    ServiceResult getChooseCourseByTeacherId(Integer teacherId);

    ServiceResult getChooseCourseByTeacherName(String teacherName);

    ServiceResult getChooseCourseByDepartmentId(Integer departmentId);
}
