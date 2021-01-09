package com.dbDesign.Jacky.service.viewService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.viewMapper.GradeViewMapper;
import com.dbDesign.Jacky.model.entity.view.GradeView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.GradeViewService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName GradeViewServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("gradeView")
@Transactional
public class GradeViewServiceImpl implements GradeViewService {
    private GradeViewMapper gradeViewMapper;

    @Autowired
    public void setGradeViewMapper(GradeViewMapper gradeViewMapper) {
        this.gradeViewMapper = gradeViewMapper;
    }

    @Override
    public ServiceResult getAllGrade() {
        QueryWrapper<GradeView> wrapper = new QueryWrapper<>();
        // 按课程号升序排出
        wrapper.orderByAsc("course_id");
        // 按总评成绩降序排出
        wrapper.orderByDesc("total_grade");
        List<GradeView> gradeViews = gradeViewMapper.selectList(wrapper);
        if (gradeViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("grades", gradeViews);
    }

    @Override
    public ServiceResult getGradesByOption(GradeView gradeView) {
        // 创建条件查询器
        QueryWrapper<GradeView> wrapper = new QueryWrapper<>();
        // 获取各属性
        Integer studentId = gradeView.getStudentId();
        String studentName = gradeView.getStudentName();
        Integer courseId = gradeView.getCourseId();
        String courseName = gradeView.getCourseName();
        String teacherName = gradeView.getTeacherName();
        Integer departmentId = gradeView.getDepartmentId();
        // 判断查询条件是否含有学生id
        if (studentId != null) {
            wrapper.eq("student_id", studentId);
        }
        if (!ParamUtil.isParamNull(studentName)) {
            wrapper.like("student_name", studentName);
        }
        if (courseId != null) {
            wrapper.eq("course_id", courseId);
        }
        if (!ParamUtil.isParamNull(courseName)) {
            wrapper.like("course_name", courseName);
        }
        if (!ParamUtil.isParamNull(teacherName)) {
            wrapper.like("teacher_name", teacherName);
        }
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        // 按课程号升序排出
        wrapper.orderByAsc("course_id");
        // 按总评成绩降序排出
        wrapper.orderByDesc("total_grade");
        List<GradeView> gradeViews = gradeViewMapper.selectList(wrapper);
        if (gradeViews.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("grades", gradeViews);
    }
}
