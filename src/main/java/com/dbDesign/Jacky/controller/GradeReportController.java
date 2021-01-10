package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.view.GradeReportView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.GradeReportViewService;
import com.dbDesign.Jacky.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GradeReportController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/gradeReport")
public class GradeReportController {
    private GradeReportViewService gradeReportViewService;

    @Autowired
    public void setGradeReportViewService(GradeReportViewService gradeReportViewService) {
        this.gradeReportViewService = gradeReportViewService;
    }

    @GetMapping("/all")
    @ResponseBody
    public JSONResponse gradeReports() {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getAllGradeReport();
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取gradeReport集合
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            // 返回成功信息
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/courseId")
    @ResponseBody
    public JSONResponse gradeReportByCourseId(@RequestParam("course_id") Integer courseId) {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByCourseId(courseId);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取对应的report记录
            GradeReportView gradeReport = (GradeReportView) serviceResult.getData().get("gradeReport");
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReport);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/courseIdList")
    @ResponseBody
    public JSONResponse gradeReportByCourseIdList(
            @RequestParam String param) {
        JSONObject paramJSONObject = JSONObject.parseObject(param);
        JSONArray idListJSONArray = paramJSONObject.getJSONArray("idList");
        List<Integer> idList = idListJSONArray.toJavaList(Integer.class);
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByCourseIdList(idList);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取对应的report记录
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/courseName")
    @ResponseBody
    public JSONResponse gradeReportsByCourseName(@RequestParam String courseName) {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByCourseName(courseName);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取gradeReport集合
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            // 返回成功信息
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/teacherId")
    @ResponseBody
    public JSONResponse gradeReportsByTeacherId(@RequestParam Integer teacherId) {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByTeacherId(teacherId);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取gradeReport集合
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            // 返回成功信息
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/teacherName")
    @ResponseBody
    public JSONResponse gradeReportsByTeacherName(@RequestParam String teacherName) {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByTeacherName(teacherName);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取gradeReport集合
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            // 返回成功信息
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/departmentId")
    @ResponseBody
    public JSONResponse gradeReportsByDepartmentId(@RequestParam Integer departmentId) {
        ServiceResult serviceResult;
        try {
            serviceResult = gradeReportViewService.getGradeReportByDepartmentId(departmentId);
        } catch (Exception ex) {
            // 捕获异常并返回失败信息
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取gradeReport集合
            List<GradeReportView> gradeReports = ListUtil.castList(
                    serviceResult.getData().get("gradeReports"), GradeReportView.class);
            // 返回成功信息
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(gradeReports);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
