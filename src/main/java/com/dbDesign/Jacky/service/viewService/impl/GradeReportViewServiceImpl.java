package com.dbDesign.Jacky.service.viewService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.CourseMapper;
import com.dbDesign.Jacky.mapper.TeacherMapper;
import com.dbDesign.Jacky.mapper.viewMapper.GradeReportViewMapper;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.entity.view.GradeReportView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.GradeReportViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GradeReportViewServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("gradeReportViewService")
@Transactional
public class GradeReportViewServiceImpl implements GradeReportViewService {
    private GradeReportViewMapper gradeReportViewMapper;
    private TeacherMapper teacherMapper;
    private CourseMapper courseMapper;

    @Autowired
    public void setGradeReportViewMapper(GradeReportViewMapper gradeReportViewMapper) {
        this.gradeReportViewMapper = gradeReportViewMapper;
    }

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public ServiceResult getAllGradeReport() {
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(null);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }

    @Override
    public ServiceResult getGradeReportByCourseId(Integer courseId) {
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        GradeReportView gradeReport = gradeReportViewMapper.selectOne(wrapper);
        return ServiceResult.ok("gradeReport", gradeReport);
    }

    @Override
    public ServiceResult getGradeReportByCourseIdList(List<Integer> courseIdList) {
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id",courseIdList);
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(wrapper);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }

    @Override
    public ServiceResult getGradeReportByCourseName(String courseName) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.like("name", courseName);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        List<Integer> courseIdList = new ArrayList<>();
        // 获取符合课程名的课程的id集合
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(wrapper);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }

    @Override
    public ServiceResult getGradeReportByTeacherId(Integer teacherId) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", teacherId);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        List<Integer> courseIdList = new ArrayList<>();
        // 获取符合课程名的课程的id集合
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(wrapper);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }

    @Override
    public ServiceResult getGradeReportByTeacherName(String teacherName) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.like("name", teacherName);
        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 符合teacherName的teacher的id集合
        List<Integer> teacherIdList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherIdList.add(teacher.getId());
        }
        // 查询出满足teacherName的teacher的课程集合
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.in("teacher_id", teacherIdList);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 符合teacherIdList的课程的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(wrapper);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }

    @Override
    public ServiceResult getGradeReportByDepartmentId(Integer departmentId) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("department_id", departmentId);
        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 符合teacherName的teacher的id集合
        List<Integer> teacherIdList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherIdList.add(teacher.getId());
        }
        // 查询出满足teacherName的teacher的课程集合
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.in("teacher_id", teacherIdList);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 符合teacherIdList的课程的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<GradeReportView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<GradeReportView> gradeReportViews = gradeReportViewMapper.selectList(wrapper);
        if (gradeReportViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("gradeReports", gradeReportViews);
    }
}
