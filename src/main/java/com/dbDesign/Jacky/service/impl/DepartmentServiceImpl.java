package com.dbDesign.Jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.DepartmentMapper;
import com.dbDesign.Jacky.mapper.StudentMapper;
import com.dbDesign.Jacky.mapper.TeacherMapper;
import com.dbDesign.Jacky.model.entity.Department;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.DepartmentService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentMapper departmentMapper;
    private StudentMapper studentMapper;
    private TeacherMapper teacherMapper;

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public ServiceResult saveDepartment(Department department) {
        // 返回结果map
        HashMap<String, Object> resultData = new HashMap<>();
        // 判断department是否含有id，有则更新，没有则插入
        if (department.getId() == null) {
            // 插入操作
            int insert = departmentMapper.insert(department);
            if (insert > 0) {
                resultData.put("insert", insert);
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail();
            }
        } else {
            // 更新操作
            int update = departmentMapper.updateById(department);
            if (update > 0) {
                resultData.put("update", update);
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail(CodeEnum.NULL_RESULT);
            }
        }
    }

    @Override
    public ServiceResult getDepartmentByDepartmentId(Integer id) {
        // 根据departmentId查询出数据
        Department department = departmentMapper.selectById(id);
        // 判断查询结果是否为空
        if (department == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("department", department);
    }

    @Override
    public ServiceResult getDepartmentByStudentId(Integer id) {
        // 查询出指定studentId对应的student数据
        Student student = studentMapper.selectById(id);
        // 判断查询结果是否为空
        if (student == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 获得查询得到的student对应的departmentId
        Integer departmentId = student.getDepartmentId();
        // 查询出该departmentId对应的department
        Department department = departmentMapper.selectById(departmentId);
        // 判断查询结果是否为空
        if (department == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("department", department);
    }

    @Override
    public ServiceResult getDepartmentByTeacherId(Integer id) {
        // 查询出指定teacherId对应的teacher数据
        Teacher teacher = teacherMapper.selectById(id);
        // 判断查询结果是否为空
        if (teacher == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 获得查询出的teacher对应的departmentId
        Integer departmentId = teacher.getDepartmentId();
        // 查询出该departmentId对应的department数据
        Department department = departmentMapper.selectById(departmentId);
        // 判断查询结果是否为空
        if (department == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("department", department);
    }

    @Override
    public ServiceResult getDepartmentListByOption(Department department) {
        // 获取各属性的值
        Integer id = department.getId();
        String name = department.getName();
        // 构造条件查询器
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (!ParamUtil.isParamNull(name)) {
            wrapper.like("name", name);
        }
        // 查询满足条件的department集合
        List<Department> departments = departmentMapper.selectList(wrapper);
        if (departments.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("departments", departments);
    }

    @Override
    public ServiceResult remoteDepartmentById(Integer id) {
        // 首先尝试查询出指定id的department数据
        Department department = departmentMapper.selectById(id);
        // 判断查询结果是否为空
        if (department == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 执行逻辑删除
        departmentMapper.deleteByIdWithFill(new Department(id));
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult getDepartmentList() {
        // 从数据库中查询得到department列表
        List<Department> departments = departmentMapper.selectList(null);
        // 判断查询结果是否为空
        if (departments.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("departments", departments);
    }
}
