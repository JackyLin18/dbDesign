package com.dbDesign.Jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.StudentHistoryMapper;
import com.dbDesign.Jacky.model.entity.StudentHistory;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.StudentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName StudentHistoryServiceImpl
 * @Author Jacky
 * @Description
 **/
@Transactional
@Service("studentHistoryService")
public class StudentHistoryServiceImpl implements StudentHistoryService {
    private StudentHistoryMapper studentHistoryMapper;

    @Autowired
    public void setStudentHistoryMapper(StudentHistoryMapper studentHistoryMapper) {
        this.studentHistoryMapper = studentHistoryMapper;
    }

    @Override
    public ServiceResult getAllStudentHistory() {
        QueryWrapper<StudentHistory> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("reason");
        List<StudentHistory> studentHistories = studentHistoryMapper.selectList(wrapper);
        if (studentHistories.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("studentHistories", studentHistories);
    }

    @Override
    public ServiceResult getStudentHistoryByStudentId(Integer studentId) {
        // 查询出指定id的studentHistory
        StudentHistory studentHistory = studentHistoryMapper.selectById(studentId);
        // 判断查询结果是否为空
        if (studentHistory == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("studentHistory", studentHistory);
    }

    @Override
    public ServiceResult getStudentHistoryListByDepartmentId(Integer departmentId) {
        // 构建查询条件器
        QueryWrapper<StudentHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", departmentId);
        // 查询出符合条件的studentHistory集合
        List<StudentHistory> studentHistories = studentHistoryMapper.selectList(wrapper);
        // 判断查询结果是否为空
        if (studentHistories.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("studentHistories", studentHistories);
    }

    @Override
    public ServiceResult remoteStudentHistoryByStudentId(Integer studentId) {
//        studentHistoryMapper.deleteByIdWithFill(new StudentHistory(studentId));
        return ServiceResult.ok();
    }
}
