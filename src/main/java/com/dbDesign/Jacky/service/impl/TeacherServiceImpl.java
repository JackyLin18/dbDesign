package com.dbDesign.Jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.CourseMapper;
import com.dbDesign.Jacky.mapper.TeacherMapper;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.TeacherService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName TeacherServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private TeacherMapper teacherMapper;
    private CourseMapper courseMapper;

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public ServiceResult saveTeacher(Teacher teacher) {
        // 返回结果
        HashMap<String, Object> resultData = new HashMap<>();
        // 判断teacher是否含有id
        if (teacher.getId() == null) {
            // teacher中不含id，进行插入操作
            // 设置默认密码 000000
            teacher.setPassword("000000");
            int insert = teacherMapper.insert(teacher);
            if (insert > 0) {
                resultData.put("insert", insert);
                resultData.put("id", teacher.getId());
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail();
            }
        } else {
            // student中含有id，进行更新操作
            int update = teacherMapper.updateById(teacher);
            if (update > 0) {
                resultData.put("update", update);
                resultData.put("id", teacher.getId());
                return ServiceResult.ok().setData(resultData);
            } else {
                return ServiceResult.fail();
            }
        }
    }

    @Override
    public ServiceResult getTeacherByTeacherId(Integer teacherId) {
        // 查询出指定id的teacher数据
        Teacher teacher = teacherMapper.selectById(teacherId);
        // 判断查询结果是否为空
        if (teacher == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        } else {
            return ServiceResult.ok("teacher", teacher);
        }
    }

    @Override
    public ServiceResult getTeacherByCourseId(Integer courseId) {
        // 查询出courseId对应的course记录
        Course course = courseMapper.selectById(courseId);
        // 判断查询结果是否为空
        if (course == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 获得course对应的teacher的id
        Integer teacherId = course.getTeacherId();
        // 从数据库查询出该teacherId对应的teacher
        Teacher teacher = teacherMapper.selectById(teacherId);
        // 判断查询结果是否为空
        if (teacher == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("teacher", teacher);
    }

    @Override
    public ServiceResult getTeachersByDepartmentId(Integer departmentId) {
        // 构建查询条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", departmentId);
        // 查出符合departmentId的teacher集合
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        // 判断查询结果是否为空
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("teachers", teachers);
    }

    @Override
    public ServiceResult remoteTeacherByTeacherId(Integer id) {
        // 从数据库中删除指定id的teacher
        int delete = teacherMapper.deleteById(id);
        // 判断删除是否成功
        if (delete > 0) {
            return ServiceResult.ok();
        }
        // 删除失败
        return ServiceResult.fail(CodeEnum.NULL_RESULT);
    }

    @Override
    public ServiceResult getAllTeacher() {
        List<Teacher> teachers = teacherMapper.selectList(null);
        if(teachers.size() == 0){
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("teachers",teachers);
    }

    @Override
    public ServiceResult loginTeacher(Integer id, String inputPassword) {
        // 获取正确的登录密码
        String rightPassword = teacherMapper.selectPasswordByTeacherId(id);
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

    @Override
    public ServiceResult getTeacherListByOption(Teacher teacher) {
        // 获取各个属性值
        Integer id = teacher.getId();
        String name = teacher.getName();
        Integer departmentId = teacher.getDepartmentId();
        String title = teacher.getTitle();
        String major = teacher.getMajor();
        // 构建条件查询器
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (!ParamUtil.isParamNull(name)) {
            wrapper.like("name", name);
        }
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        if (!ParamUtil.isParamNull(title)) {
            wrapper.like("title", title);
        }
        if (!ParamUtil.isParamNull(major)) {
            wrapper.like("major", major);
        }
        // 查询满足条件的teacher集合
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("teachers", teachers);
    }
}
