package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.intermediateService.StudentCourseService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName StudentCourseController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/studentCourse")
public class StudentCourseController {
    private StudentCourseService studentCourseService;

    @Autowired
    public void setStudentCourseService(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    /**
     * @Author Jacky
     * @Param params 选课的信息
     * @Description 选课
     **/
    @PostMapping("/choose")
    @ResponseBody
    public JSONResponse choose(@RequestParam String params) {
        // 判断必要参数
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 构建studentCourse对象
        StudentCourse studentCourse = new StudentCourse();
        // 获取学生id
        Integer studentId = paramsJSONObject.getInteger("student_id");
        studentCourse.setStudentId(studentId);
        // 获取课程id
        Integer courseId = paramsJSONObject.getInteger("course_id");
        studentCourse.setCourseId(courseId);

        ServiceResult serviceResult;
        try {
            // 选课
            serviceResult = studentCourseService.chooseCourse(studentCourse);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else {
            return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue().setMessage(serviceResult.getMessage());
        }
    }

    /**
     * @Author Jacky
     * @Param params 需要保存的成绩信息
     * @Description 保存成绩（添加或更新）
     **/
    @PostMapping("/grade")
    @ResponseBody
    public JSONResponse postGrade(@RequestParam String params) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 构建studentCourse对象
        StudentCourse studentCourse = new StudentCourse();
        // 获取student_id
        Integer studentId = paramsJSONObject.getInteger("student_id");
        studentCourse.setStudentId(studentId);
        // 获取course_id
        Integer courseId = paramsJSONObject.getInteger("course_id");
        studentCourse.setCourseId(courseId);
        // 获取成绩
        BigDecimal grade = paramsJSONObject.getBigDecimal("grade");
        studentCourse.setGrade(grade);

        ServiceResult serviceResult;
        try {
            // 保存成绩
            serviceResult = studentCourseService.saveGrade(studentCourse);
        } catch (Exception ex) {
            // 捕获异常并返回
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param courseId 指定的 student 的 id
     * @Description 查询该 student 的所有成绩
     **/
    @GetMapping("/grades/student")
    @ResponseBody
    public JSONResponse getGradesByStudent(@RequestParam Integer studentId) {
        // 判断必要参数是否为空
        if (studentId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定学生的成绩集合
            serviceResult = studentCourseService.getGradesByStudentId(studentId);
        } catch (Exception ex) {
            // 捕获异常并返回
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取成绩集合
            List<StudentCourse> studentCourses = ListUtil.castList(
                    serviceResult.getData().get("studentCourses"), StudentCourse.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(studentCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/grades/course")
    @ResponseBody
    public JSONResponse getGradesByCourse(@RequestParam Integer courseId) {
        // 判断必要参数是否为空
        if (courseId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定科目的学生成绩集合
            serviceResult = studentCourseService.getGradesByCourseId(courseId);
        } catch (Exception ex) {
            // 捕获异常并返回
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取成绩集合
            List<StudentCourse> studentCourses = ListUtil.castList(
                    serviceResult.getData().get("studentCourses"), StudentCourse.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(studentCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param params 查询成绩的参数
     * @Description 查询指定学生的指定科目成绩
     **/
    @GetMapping("/grade/student")
    @ResponseBody
    public JSONResponse getStudentGrade(@RequestParam String params) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取student_id
        Integer studentId = paramsJSONObject.getInteger("student_id");
        // 获取course_id
        Integer courseId = paramsJSONObject.getInteger("course_id");
        if (studentId == null || courseId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }

        ServiceResult serviceResult;
        try {
            // 获取成绩
            serviceResult = studentCourseService.getGrade(studentId, courseId);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取成绩
            BigDecimal grade = (BigDecimal) serviceResult.getData().get("grade");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(grade);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param params 取消选课的参数
     * @Description 取消选课
     **/
    @DeleteMapping("/cancelChoose")
    @ResponseBody
    public JSONResponse cancelChoose(@RequestParam String params) {
        // 判断必要参数
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取学生id
        Integer studentId = paramsJSONObject.getInteger("student_id");
        // 获取课程id
        Integer courseId = paramsJSONObject.getInteger("course_id");

        ServiceResult serviceResult;
        try {
            // 取消选课
            serviceResult = studentCourseService.remoteStudentCourse(studentId, courseId);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 取消选课成功
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();

    }
}
