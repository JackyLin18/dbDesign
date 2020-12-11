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

import java.util.HashMap;
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
    public ServiceResult saveStudentHistory(StudentHistory studentHistory) {
        // 获取该studentHistory对应的id
        Integer id = studentHistory.getId();
        // 判断是否含有id
        if (id == null) {
            // 不含id，返回参数不全状态
            return ServiceResult.fail(CodeEnum.PARAMETER_MISSING);
        }
        // 构建返回结果map
        HashMap<String, Object> resultMap = new HashMap<>();
        // 判断数据库中是否已经含有该id对应的数据
        if (studentHistoryMapper.selectById(id) == null) {
            // 进行插入操作
            int insert = studentHistoryMapper.insert(studentHistory);
            if (insert > 0) {
                resultMap.put("insert", insert);
                return ServiceResult.ok().setData(resultMap);
            }
        } else {
            // 进行更新操作
            int update = studentHistoryMapper.updateById(studentHistory);
            if (update > 0) {
                resultMap.put("update", update);
                return ServiceResult.ok().setData(resultMap);
            }
        }
        return ServiceResult.fail();
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
        studentHistoryMapper.deleteByIdWithFill(new StudentHistory(studentId));
        return ServiceResult.ok();
    }
}
