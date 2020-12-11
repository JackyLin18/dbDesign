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
    public ServiceResult saveStudentCourse(StudentCourse studentCourse) {
        // 构建返回结果
        HashMap<String, Object> resultMap = new HashMap<>();
        // 尝试从数据库中查询是否有该学生选择该课程
        // 构建查询条件
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentCourse.getStudentId());
        wrapper.eq("course_id", studentCourse.getCourseId());
        // 查询符合条件的studentCourse数据
        StudentCourse sc = studentCourseMapper.selectOne(wrapper);
        // 判断查询结果是否为空
        if (sc == null) {
            // 查询结果为空，进行插入操作
            int insert = studentCourseMapper.insert(studentCourse);
            resultMap.put("insert", insert);
        } else {
            // 查询结果不为空，进行更新操作
            int update = studentCourseMapper.update(studentCourse, wrapper);
            resultMap.put("update", update);
        }
        return ServiceResult.ok().setData(resultMap);
    }

    @Override
    public ServiceResult getStudentListByCourseId(Integer courseId) {
        // 查询出选修了指定courseId的student的studentId集合
        List<Integer> studentIdList = studentCourseMapper.selectStudentIdListByCourseId(courseId);
        // 判断查询结果是否为空
        if (studentIdList.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 批量查询出所有student
        List<Student> students = studentMapper.selectBatchIds(studentIdList);
        // 判断查询结果是否为空
        if (students.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("students", students);
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
