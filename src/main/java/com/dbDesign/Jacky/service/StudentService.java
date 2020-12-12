package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.vo.ServiceResult;

/**
 * @ClassName StudentService
 * @Author Jacky
 * @Description
 **/
public interface StudentService {
    ServiceResult saveStudent(Student student);

    ServiceResult getStudentByStudentId(Integer id);

    ServiceResult getStudentListByDepartmentId(Integer departmentId);

    ServiceResult getStudentListByCourseId(Integer courseId);

    ServiceResult getStudentListByOption(Student student,Integer scoreType);

    ServiceResult getAllStudentList();

    ServiceResult remoteStudentByStudentId(Integer studentId,Integer reason);

    ServiceResult loginStudent(Integer id,String inputPassword);
}
