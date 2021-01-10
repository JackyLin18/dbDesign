package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.view.ChooseCourseView;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.viewService.ChooseCourseViewService;
import com.dbDesign.Jacky.util.ListUtil;
import net.sf.jsqlparser.statement.select.ExceptOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName ChooseCourseController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/chooseCourse")
public class ChooseCourseController {
    private ChooseCourseViewService chooseCourseViewService;

    @Autowired
    public void setChooseCourseViewService(ChooseCourseViewService chooseCourseViewService) {
        this.chooseCourseViewService = chooseCourseViewService;
    }

    @GetMapping("/all")
    @ResponseBody
    public JSONResponse chooseCourses() {
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getAllChooseCourse();
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            // 返回成功状态
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/courseIdList")
    @ResponseBody
    public JSONResponse chooseCoursesByCourseIdList(@RequestParam String param) {
        JSONObject paramJSONObject = JSONObject.parseObject(param);
        JSONArray idListJSONArray = paramJSONObject.getJSONArray("idList");
        List<Integer> idList = idListJSONArray.toJavaList(Integer.class);
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getChooseCourseByCourseIdList(idList);
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/courseName")
    @ResponseBody
    public JSONResponse chooseCoursesByCourseName(@RequestParam String courseName) {
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getChooseCourseByCourseName(courseName);
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/teacherId")
    @ResponseBody
    public JSONResponse chooseCoursesByTeacherId(@RequestParam Integer teacherId) {
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getChooseCourseByTeacherId(teacherId);
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/teacherName")
    @ResponseBody
    public JSONResponse chooseCoursesByTeacherName(@RequestParam String teacherName) {
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getChooseCourseByTeacherName(teacherName);
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/departmentId")
    @ResponseBody
    public JSONResponse chooseCoursesByDepartmentId(@RequestParam Integer departmentId) {
        ServiceResult serviceResult;
        try {
            serviceResult = chooseCourseViewService.getChooseCourseByDepartmentId(departmentId);
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
            // 获取chooseCourse集合
            List<ChooseCourseView> chooseCourses = ListUtil.castList(
                    serviceResult.getData().get("chooseCourses"), ChooseCourseView.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(chooseCourses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
