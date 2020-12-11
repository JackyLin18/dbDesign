package com.dbDesign.Jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.StudentHistoryMapper;
import com.dbDesign.Jacky.mapper.StudentMapper;
import com.dbDesign.Jacky.mapper.intermediateMapper.StudentCourseMapper;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.entity.StudentHistory;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.StudentService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName StudentServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;
    private StudentCourseMapper studentCourseMapper;
    private StudentHistoryMapper studentHistoryMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setStudentCourseMapper(StudentCourseMapper studentCourseMapper) {
        this.studentCourseMapper = studentCourseMapper;
    }

    @Autowired
    public void setStudentHistoryMapper(StudentHistoryMapper studentHistoryMapper) {
        this.studentHistoryMapper = studentHistoryMapper;
    }

    @Override
    public ServiceResult saveStudent(Student student) {
        // 返回结果
        HashMap<String, Object> resultData = new HashMap<>();
        // 判断student是否含有id
        if (student.getId() == null) {
            // student中不含有id，进行插入操作
            int insert = studentMapper.insert(student);
            if (insert > 0) {
                resultData.put("insert", insert);
                resultData.put("id", student.getId());
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail();
            }
        } else {
            // student中含有id，进行更新操作
            int update = studentMapper.updateById(student);
            if (update > 0) {
                resultData.put("update", update);
                resultData.put("id", student.getId());
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail();
            }
        }
    }

    @Override
    public ServiceResult getStudentByStudentId(Integer id) {
        // 查询出指定id的student数据
        Student student = studentMapper.selectById(id);
        // 判断是否为空
        if (student == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("student", student);
    }

    @Override
    public ServiceResult getStudentListByDepartmentId(Integer departmentId) {
        // 构造查询条件
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", departmentId);
        // 查询出符合指定查询条件的student集合
        List<Student> students = studentMapper.selectList(wrapper);
        // 判断查询结果是否为空
        if (students.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("students", students);
    }

    @Override
    public ServiceResult getStudentListByCourseId(Integer courseId) {
        // 查询出选修了指定courseId的学生的studentId集合
        List<Integer> studentIdList = studentCourseMapper.selectStudentIdListByCourseId(courseId);
        // 判断studentId集合是否为空
        if (studentIdList.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 根据studentId集合，批量查询
        List<Student> students = studentMapper.selectBatchIds(studentIdList);
        // 判断查询结果是否为空
        if (students.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("students", students);
    }

    @Override
    public ServiceResult getStudentListByOption(Student student, Integer scoreType) {
        // 获取各个属性值
        Integer id = student.getId();
        String name = student.getName();
        Integer enrolledScore = student.getEnrolledScore();
        Integer departmentId = student.getDepartmentId();
        // 构建条件查询器
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (!ParamUtil.isParamNull(name)) {
            wrapper.like("name", name);
        }
        if (enrolledScore != null) {
            if (scoreType == 1) {
                wrapper.gt("enrolled_score", enrolledScore);
            } else if (scoreType == 2) {
                wrapper.lt("enrolled_score", enrolledScore);
            } else {
                wrapper.eq("enrolled_score", enrolledScore);
            }
        }
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        // 查询满足条件的student集合
        List<Student> students = studentMapper.selectList(wrapper);
        if (students.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("students", students);
    }

    @Override
    public ServiceResult remoteStudentByStudentId(Integer studentId, Integer reason) {
        // 查询出指定studentId的student数据
        Student student = studentMapper.selectById(studentId);
        // 判断结果是否为空
        if (student == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 向下转换为studentHistory对象
        StudentHistory studentHistory = (StudentHistory) student;
        studentHistory.setReason(reason);
        // 向数据库中插入studentHistory
        int insert = studentHistoryMapper.insert(studentHistory);
        // 判断是否插入成功
        if (insert > 0) {
            // 插入成功
            // 将student数据从数据库中删除
            int delete = studentMapper.deleteById(studentId);
            if (delete > 0) {
                // 删除成功
                return ServiceResult.ok();
            } else {
                // 删除失败，删除studentHistory
                studentHistoryMapper.deleteById(studentId);
            }
        }
        // 插入失败
        return ServiceResult.fail();
    }

    @Override
    public ServiceResult loginStudent(Integer id, String inputPassword) {
        // 获取正确的登录密码
        String rightPassword = studentMapper.selectPasswordByStudentId(id);
        // 判断查询结果是否为空
        if (ParamUtil.isParamNull(rightPassword)) {
            // 返回值为空状态
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 比较rightPassword和inputPassword
        if (rightPassword.equals(inputPassword)) {
            // 密码正确
            return ServiceResult.ok();
        }
        // 密码错误
        return ServiceResult.fail();
    }
}
