package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface TeacherService {
    ServiceResult saveTeacher(Teacher teacher);

    ServiceResult getTeacherByTeacherId(Integer teacherId);

    ServiceResult getTeacherByCourseId(Integer courseId);

    ServiceResult getTeachersByDepartmentId(Integer departmentId);

    ServiceResult getTeacherListByOption(Teacher teacher);

    ServiceResult getTeacherIdListByName(String name);

    ServiceResult getAllTeacher();

    ServiceResult remoteTeacherByTeacherId(Integer id);

    ServiceResult loginTeacher(Integer id,String inputPassword);
}
