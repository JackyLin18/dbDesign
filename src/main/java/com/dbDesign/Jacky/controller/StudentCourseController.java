package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.intermediateService.StudentCourseService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
     * @Param params 取消选课的参数
     * @Description 取消选课
     **/
    @DeleteMapping("/cancelChoose")
    @ResponseBody
    public JSONResponse cancelChoose(@RequestParam String params){
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
        try{
            // 取消选课
            serviceResult = studentCourseService.remoteStudentCourse(studentId,courseId);
        }catch (Exception ex){
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if(resultCode.equals(CodeEnum.SUCCESS.getCode())){
            // 取消选课成功
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        }else if(resultCode.equals(CodeEnum.NULL_RESULT.getCode())){
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();

    }
}
