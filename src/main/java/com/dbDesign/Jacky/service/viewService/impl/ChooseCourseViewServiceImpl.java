package com.dbDesign.Jacky.service.viewService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.CourseMapper;
import com.dbDesign.Jacky.mapper.TeacherMapper;
import com.dbDesign.Jacky.mapper.viewMapper.ChooseCourseViewMapper;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.entity.view.ChooseCourseView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.ChooseCourseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChooseCourseViewServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("chooseCourseView")
@Transactional
public class ChooseCourseViewServiceImpl implements ChooseCourseViewService {
    private ChooseCourseViewMapper chooseCourseViewMapper;
    private CourseMapper courseMapper;
    private TeacherMapper teacherMapper;

    @Autowired
    public void setChooseCourseViewMapper(ChooseCourseViewMapper chooseCourseViewMapper) {
        this.chooseCourseViewMapper = chooseCourseViewMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public ServiceResult getAllChooseCourse() {
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(null);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByCourseId(Integer courseId) {
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByCourseIdList(List<Integer> courseIdList) {
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if(chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByCourseName(String courseName) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.like("name", courseName);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 满足courseName的course的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByTeacherId(Integer teacherId) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", teacherId);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        // 满足teacherId的course的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByTeacherName(String teacherName) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.like("name", teacherName);
        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 存放满足teacherName的teacher的id集合
        List<Integer> teacherIdList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherIdList.add(teacher.getId());
        }
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.in("teacher_id", teacherIdList);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        // 满足teacherId的course的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }

    @Override
    public ServiceResult getChooseCourseByDepartmentId(Integer departmentId) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("department_id", departmentId);
        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        if (teachers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        // 存放满足departmentId的teacher的id集合
        List<Integer> teacherIdList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherIdList.add(teacher.getId());
        }
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.in("teacher_id", teacherIdList);
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        // 满足teacherId的course的id集合
        List<Integer> courseIdList = new ArrayList<>();
        for (Course course : courses) {
            courseIdList.add(course.getId());
        }
        QueryWrapper<ChooseCourseView> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", courseIdList);
        List<ChooseCourseView> chooseCourseViews = chooseCourseViewMapper.selectList(wrapper);
        if (chooseCourseViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("chooseCourses", chooseCourseViews);
    }
}
