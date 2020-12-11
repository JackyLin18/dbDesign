package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Department;
import com.dbDesign.Jacky.model.vo.ServiceResult;

/**
 * @ClassName DepartmentService
 * @Author Jacky
 * @Description
 **/
public interface DepartmentService {
    ServiceResult saveDepartment(Department department);

    ServiceResult getDepartmentByDepartmentId(Integer id);

    ServiceResult getDepartmentByStudentId(Integer id);

    ServiceResult getDepartmentByTeacherId(Integer id);

    ServiceResult remoteDepartmentById(Integer id);

    ServiceResult getDepartmentList();
}
