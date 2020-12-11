package com.dbDesign.Jacky.service.intermediateService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.CourseMapper;
import com.dbDesign.Jacky.mapper.StudentMapper;
import com.dbDesign.Jacky.mapper.intermediateMapper.StudentCourseMapper;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.intermediateService.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName StudentCourseServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("studentCourseService")
@Transactional
public class StudentCourseServiceImpl implements StudentCourseService {
    private StudentCourseMapper studentCourseMapper;
    private StudentMapper studentMapper;
    private CourseMapper courseMapper;

    @Autowired
    public void setStudentCourseMapper(StudentCourseMapper studentCourseMapper) {
        this.studentCourseMapper = studentCourseMapper;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public ServiceResult saveGrade(StudentCourse studentCourse) {
        // 获取studentId
        Integer studentId = studentCourse.getStudentId();
        // 获取courseId
        Integer courseId = studentCourse.getCourseId();
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("course_id", courseId);
        // 查询是否存在选课信息
        StudentCourse sc = studentCourseMapper.selectOne(wrapper);
        if (sc == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        int update = studentCourseMapper.update(studentCourse, wrapper);
        if (update > 0) {
            return ServiceResult.ok();
        }
        return ServiceResult.fail();
    }

    @Override
    public ServiceResult chooseCourse(StudentCourse studentCourse) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentCourse.getStudentId());
        wrapper.eq("course_id", studentCourse.getCourseId());
        // 查询该学生是否已选择该课
        StudentCourse sc = studentCourseMapper.selectOne(wrapper);
        if (sc != null) {
            return ServiceResult.fail(CodeEnum.OTHER_ERROR).setMessage("不可重复选课");
        }
        // 查询该student当前选课的学分数
        Double nowCreditDouble = studentCourseMapper.selectCreditCountByStudentId(studentCourse.getStudentId());
        if (nowCreditDouble == null) {
            nowCreditDouble = 0.0;
        }
        BigDecimal nowCredit = BigDecimal.valueOf(nowCreditDouble);
        // 查询该course的学分数
        Course course = courseMapper.selectById(studentCourse.getCourseId());
        BigDecimal credit = course.getCredit();
        // 计算选课后的和
        BigDecimal newCredit = nowCredit.add(credit);
        // 判断是否超过15分
        if (newCredit.doubleValue() > 15.0) {
            return ServiceResult.fail(CodeEnum.OTHER_ERROR).setMessage("选课超过15学分");
        } else {
            int insert = studentCourseMapper.insert(studentCourse);
            if (insert > 0) {
                return ServiceResult.ok();
            }
        }
        return ServiceResult.fail();
    }

    @Override
    public ServiceResult getCourseListByStudentId(Integer studentId) {
        // 查询出指定student选修的course的id集合
        List<Integer> courseIdList = studentCourseMapper.selectCourseIdListByStudentId(studentId);
        // 判断查询结果是否为空
        if (courseIdList.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 批量查询出所有的course
        List<Course> courses = courseMapper.selectBatchIds(courseIdList);
        // 判断查询结果是否为空
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("courses", courses);
    }

    @Override
    public ServiceResult remoteStudentCourse(Integer studentId, Integer courseId) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("course_id", courseId);
        // 删除符合条件的数据
        int delete = studentCourseMapper.delete(wrapper);
        // 判断删除结果
        if (delete > 0) {
            // 删除成功
            return ServiceResult.ok();
        } else {
            // 删除失败
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
    }

    @Override
    public ServiceResult getGrade(Integer studentId, Integer courseId) {
        // 构造条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("course_id", courseId);
        StudentCourse studentCourse = studentCourseMapper.selectOne(wrapper);
        if (studentCourse == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        BigDecimal grade = studentCourse.getGrade();
        if (grade == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("grade", grade);
    }

    @Override
    public ServiceResult getGradesByCourseId(Integer courseId) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(wrapper);
        if (studentCourses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("studentCourses", studentCourses);
    }

    @Override
    public ServiceResult getGradesByStudentId(Integer studentId) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(wrapper);
        if (studentCourses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("studentCourses", studentCourses);
    }

    @Override
    public ServiceResult remoteByStudentId(Integer studentId) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        // 删除符合条件的数据
        int delete = studentCourseMapper.delete(wrapper);
        // 判断删除结果
        if (delete > 0) {
            // 删除成功
            return ServiceResult.ok("delete", delete);
        } else {
            // 删除失败
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
    }

    @Override
    public ServiceResult remoteByCourseId(Integer courseId) {
        // 构建条件查询器
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        // 删除符合条件的数据
        int delete = studentCourseMapper.delete(wrapper);
        // 判断删除结果
        if (delete > 0) {
            // 删除成功
            return ServiceResult.ok("delete", delete);
        } else {
            // 删除失败
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
    }
}
