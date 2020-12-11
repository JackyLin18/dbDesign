package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.TeacherService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

/**
 * @ClassName TeacherController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * @Author Jacky
     * @Param params 需要保存的 teacher 参数 JSON 字符串
     * @Description 保存 teacher 数据（新增/更新）
     **/
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public JSONResponse teacher(@RequestParam String params, HttpServletRequest request) throws ParseException {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建teacher对象
        Teacher teacher = new Teacher();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        teacher.setId(id);
        // 获取登录密码
        String password = paramJSONObject.getString("password");
        teacher.setPassword(password);
        // 获取name
        String name = paramJSONObject.getString("name");
        teacher.setName(name);
        // 获取sex
        Integer sex = paramJSONObject.getInteger("sex");
        teacher.setSex(sex);
        // 获取birthday
        String birthdayMonthString = paramJSONObject.getString("birthday");
        String birthdayString = birthdayMonthString + "-01";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        teacher.setBirthday(new Date(simpleDateFormat.parse(birthdayString).getTime()));
        // 获取departmentId
        Integer departmentId = paramJSONObject.getInteger("department_id");
        teacher.setDepartmentId(departmentId);
        // 获取title
        String title = paramJSONObject.getString("title");
        teacher.setTitle(title);
        // 获取major
        String major = paramJSONObject.getString("major");
        teacher.setMajor(major);
        // 获取teachingDirection
        String teachingDirection = paramJSONObject.getString("teaching_direction");
        teacher.setTeachingDirection(teachingDirection);

        ServiceResult serviceResult;
        try {
            // 保存teacher数据
            serviceResult = teacherService.saveTeacher(teacher);
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
            // 获取teacher的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
            // 判断是否为更新操作
            Integer update = (Integer) serviceResult.getData().get("update");
            if (update != null) {
                // 将修改的teacher信息存入session
                request.getSession().setAttribute("teacher", teacher);
            }
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(resultId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param optionParams 指定的查询条件
     * @Description 根据多个查询条件查询 teacher
     **/
    @GetMapping("/option")
    @ResponseBody
    public JSONResponse teacherByOption(@RequestParam String optionParams) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(optionParams)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将optionParams转换为JSONObject
        JSONObject optionJSONObject = JSONObject.parseObject(optionParams);
        // 构建teacher对象
        Teacher teacher = new Teacher();
        // 获取id
        Integer id = optionJSONObject.getInteger("id");
        teacher.setId(id);
        // 获取姓名
        String name = optionJSONObject.getString("name");
        if (!ParamUtil.isParamNull(name)) {
            teacher.setName(name);
        }
        // 获取系别
        Integer departmentId = optionJSONObject.getInteger("department_id");
        teacher.setDepartmentId(departmentId);
        // 获取职称
        String title = optionJSONObject.getString("title");
        if (!ParamUtil.isParamNull(title)) {
            teacher.setTitle(title);
        }
        // 获取专业
        String major = optionJSONObject.getString("major");
        if (!ParamUtil.isParamNull(major)) {
            teacher.setMajor(major);
        }

        ServiceResult serviceResult;
        try {
            // 查询指定条件的teacher集合
            serviceResult = teacherService.getTeacherListByOption(teacher);
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
            // 获取teacher集合
            List<Teacher> teachers = ListUtil.castList(
                    serviceResult.getData().get("teachers"), Teacher.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teachers);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping
    @ResponseBody
    public JSONResponse teacher(@RequestParam Integer id) {
        // 判断必要参数
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定id的teacher数据
            serviceResult = teacherService.getTeacherByTeacherId(id);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态
            // 获取查询得到的teacher数据
            Teacher teacher = (Teacher) serviceResult.getData().get("teacher");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teacher);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param departmentId 指定的 departmentId
     * @Description 查询出指定 departmentId 的 teacher 集合
     **/
    @GetMapping("/department")
    @ResponseBody
    public JSONResponse teachersByDepartment(@RequestParam Integer departmentId) {
        // 判断必要参数是否为空
        if (departmentId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定departmentId的教师
            serviceResult = teacherService.getTeachersByDepartmentId(departmentId);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 获取teacher集合
            List<Teacher> teachers = ListUtil.castList(
                    serviceResult.getData().get("teachers"), Teacher.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teachers);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param courseId 指定的 courseId
     * @Description 根据指定的 courseId 查询出授课的 teacher
     **/
    @GetMapping("/course")
    @ResponseBody
    public JSONResponse teacherByCourse(@RequestParam Integer courseId) {
        // 判断必要参数是否为空
        if (courseId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 根据指定的courseId获取teacher
            serviceResult = teacherService.getTeacherByCourseId(courseId);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 获取查询得到的teacher
            Teacher teacher = (Teacher) serviceResult.getData().get("teacher");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teacher);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Description 获取所有 teacher 集合
     **/
    @GetMapping("/all")
    @ResponseBody
    public JSONResponse getAllTeacher() {
        ServiceResult serviceResult;
        try {
            // 获取全部教师
            serviceResult = teacherService.getAllTeacher();
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
            // 获取teacher集合
            List<Teacher> teachers = ListUtil.castList(
                    serviceResult.getData().get("teachers"), Teacher.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teachers);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 指定的 teacherId
     * @Description 删除指定 id 的 teacher 数据
     **/
    @DeleteMapping
    @ResponseBody
    public JSONResponse deleteTeacherById(@RequestParam Integer id) {
        // 判断必要参数是否存在
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 删除指定的teacher的记录
            serviceResult = teacherService.remoteTeacherByTeacherId(id);
        } catch (Exception ex) {
            // 捕获异常并返回
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 返回响应
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
