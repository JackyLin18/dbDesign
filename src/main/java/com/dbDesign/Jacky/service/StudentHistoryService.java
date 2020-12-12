package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface StudentHistoryService {
    ServiceResult getAllStudentHistory();

    ServiceResult getStudentHistoryByStudentId(Integer studentId);

    ServiceResult getStudentHistoryListByDepartmentId(Integer departmentId);

    ServiceResult remoteStudentHistoryByStudentId(Integer studentId);
}
