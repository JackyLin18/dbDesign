package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.view.GradeView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.GradeViewService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName GradeController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/grade")
public class GradeController {
    private GradeViewService gradeViewService;

    @Autowired
    public void setGradeViewService(GradeViewService gradeViewService) {
        this.gradeViewService = gradeViewService;
    }

    /**
     * @Author Jacky
     * @Description 查询所有成绩相关信息
     **/
    @GetMapping("/all")
    @ResponseBody
    public JSONResponse grades() {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeViewService.getAllGrade();
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
            // 获取grade集合
            List<GradeView> grades = ListUtil.castList(
                    serviceResult.getData().get("grades"), GradeView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(grades);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/option")
    @ResponseBody
    public JSONResponse gradeByOption(@RequestParam String optionParams) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(optionParams)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将optionParams转换为JSONObject
        JSONObject optionJSONObject = JSONObject.parseObject(optionParams);
        // 构建GradeView对象
        GradeView gradeView = new GradeView();
        // 获取studentId
        Integer studentId = optionJSONObject.getInteger("student_id");
        gradeView.setStudentId(studentId);
        // 获取studentName
        String studentName = optionJSONObject.getString("student_name");
        gradeView.setStudentName(studentName);
        // 获取courseId
        Integer courseId = optionJSONObject.getInteger("course_id");
        gradeView.setCourseId(courseId);
        // 获取courseName
        String courseName = optionJSONObject.getString("course_name");
        gradeView.setCourseName(courseName);
        // 获取teacherName
        String teacherName = optionJSONObject.getString("teacher_name");
        gradeView.setTeacherName(teacherName);
        // 获取departmentId
        Integer departmentId = optionJSONObject.getInteger("department_id");
        gradeView.setDepartmentId(departmentId);

        ServiceResult serviceResult;
        try {
            serviceResult = gradeViewService.getGradesByOption(gradeView);
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
            // 获取grade集合
            List<GradeView> grades = ListUtil.castList(
                    serviceResult.getData().get("grades"), GradeView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(grades);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
