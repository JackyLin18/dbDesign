package com.dbDesign.Jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.CourseMapper;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.CourseService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName CourseServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {
    private CourseMapper courseMapper;

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public ServiceResult saveCourse(Course course) {
        // 构建返回结果map
        HashMap<String, Object> resultMap = new HashMap<>();
        // 判断course对象是否含有id
        if (course.getId() == null) {
            // 进行插入操作
            int insert = courseMapper.insert(course);
            if (insert > 0) {
                resultMap.put("insert", insert);
                resultMap.put("id", course.getId());
                return ServiceResult.ok().setData(resultMap);
            } else {
                return ServiceResult.fail();
            }
        } else {
            // 进行更新操作
            int update = courseMapper.updateById(course);
            if (update > 0) {
                resultMap.put("update", update);
                resultMap.put("id", course.getId());
                return ServiceResult.ok().setData(resultMap);
            } else {
                return ServiceResult.fail(CodeEnum.NULL_RESULT);
            }
        }
    }

    @Override
    public ServiceResult getCourseByCourseId(Integer courseId) {
        Course course = courseMapper.selectById(courseId);
        // 判断查询结果是否为空
        if (course == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("course", course);
    }

    @Override
    public ServiceResult getCourseListByTeacherId(Integer teacherId) {
        // 构建条件查询器
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        // 查询出符合条件的course集合
        List<Course> courses = courseMapper.selectList(wrapper);
        // 判断返回结果是否为空
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("courses", courses);
    }

    @Override
    public ServiceResult getCourseListByOption(Course course, Integer courseHoursType, Integer creditType) {
        // 获取各个属性值
        Integer id = course.getId();
        String name = course.getName();
        Integer teacherId = course.getTeacherId();
        Integer courseHours = course.getCourseHours();
        BigDecimal credit = course.getCredit();
        String classTime = course.getClassTime();
        String classAddress = course.getClassAddress();
        // 构建条件查询器
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (!ParamUtil.isParamNull(name)) {
            wrapper.like("name", name);
        }
        if (teacherId != null) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (courseHours != null) {
            if (courseHoursType == 1) {
                wrapper.gt("course_hours", courseHours);
            } else if (courseHoursType == 2) {
                wrapper.lt("course_hours", courseHours);
            } else {
                wrapper.eq("course_hours", courseHours);
            }
        }
        if (credit != null) {
            if (creditType == 1) {
                wrapper.gt("credit", credit);
            } else if (creditType == 2) {
                wrapper.lt("credit", credit);
            } else {
                wrapper.eq("credit", credit);
            }
        }
        if (!ParamUtil.isParamNull(classTime)) {
            wrapper.like("class_time", classTime);
        }
        if (!ParamUtil.isParamNull(classAddress)) {
            wrapper.like("class_address", classAddress);
        }
        // 查询满足条件的course集合
        List<Course> courses = courseMapper.selectList(wrapper);
        // 判断查询结果是否为空
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("courses", courses);
    }

    @Override
    public ServiceResult getAllCourseList() {
        List<Course> courses = courseMapper.selectList(null);
        // 判断返回结果是否为空
        if (courses.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("courses", courses);
    }

    @Override
    public ServiceResult remoteCourseByCourseId(Integer courseId) {
        int delete = courseMapper.deleteById(courseId);
        if (delete > 0) {
            return ServiceResult.ok();
        } else {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
    }
}
