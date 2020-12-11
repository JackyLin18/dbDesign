package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.Administrator;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.AdministratorService;
import com.dbDesign.Jacky.service.StudentService;
import com.dbDesign.Jacky.service.TeacherService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/login")
public class LoginController {
    private StudentService studentService;
    private TeacherService teacherService;
    private AdministratorService administratorService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setAdministratorService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping("/student")
    @ResponseBody
    public JSONResponse loginStudent(@RequestParam String params, HttpServletRequest request) {
        // 判断参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转换为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取id
        Integer id = paramsJSONObject.getInteger("id");
        // 获取输入密码
        String password = paramsJSONObject.getString("password");
        // 判断参数是否齐全
        if (id == null || ParamUtil.isParamNull(password)) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }

        ServiceResult serviceResult;
        try {
            // 判断登录密码是否匹配
            serviceResult = studentService.loginStudent(id, password);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 登录成功
            // 查询出student的信息
            ServiceResult studentServiceResult;
            try {
                // 查询出指定id的student信息
                studentServiceResult = studentService.getStudentByStudentId(id);
            } catch (Exception ex) {
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
            // 获取状态码
            Integer studentResultCode = studentServiceResult.getCode();
            // 创建student实体
            Student student = null;
            // 判断状态码
            if (studentResultCode.equals(CodeEnum.SUCCESS.getCode())) {
                // 获取student数据
                student = (Student) studentServiceResult.getData().get("student");
            } else if (studentResultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
                // 返回值为空状态
                return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
            }
            // 将用户信息写入session
            HttpSession session = request.getSession();
            session.setAttribute("student", student);
            // 返回成功响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(student);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @PostMapping("/teacher")
    @ResponseBody
    public JSONResponse loginTeacher(@RequestParam String params, HttpServletRequest request) {
        // 判断参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转换为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取id
        Integer id = paramsJSONObject.getInteger("id");
        // 获取输入密码
        String password = paramsJSONObject.getString("password");
        // 判断参数是否齐全
        if (id == null || ParamUtil.isParamNull(password)) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }

        ServiceResult serviceResult;
        try {
            // 判断登录密码是否匹配
            serviceResult = teacherService.loginTeacher(id, password);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 登录成功
            // 查询出teacher的信息
            ServiceResult teacherServiceResult;
            try {
                // 查询出指定id的teacher信息
                teacherServiceResult = teacherService.getTeacherByTeacherId(id);
            } catch (Exception ex) {
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
            // 获取状态码
            Integer teacherResultCode = teacherServiceResult.getCode();
            // 创建student实体
            Teacher teacher = null;
            // 判断状态码
            if (teacherResultCode.equals(CodeEnum.SUCCESS.getCode())) {
                // 获取teacher数据
                teacher = (Teacher) teacherServiceResult.getData().get("teacher");
            } else if (teacherResultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
                // 返回值为空状态
                return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
            }
            // 将teacher信息写入session
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            // 返回成功响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(teacher);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @PostMapping("/administrator")
    @ResponseBody
    public JSONResponse loginAdministrator(@RequestParam String params, HttpServletRequest request){
        // 判断参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将参数JSON字符串转换为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取id
        Integer id = paramsJSONObject.getInteger("id");
        // 获取输入密码
        String password = paramsJSONObject.getString("password");
        // 判断参数是否齐全
        if (id == null || ParamUtil.isParamNull(password)) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }

        Administrator administrator = new Administrator(id, password);
        ServiceResult serviceResult;
        try {
            // 判断登录密码是否匹配
            serviceResult = administratorService.loginAdministrator(administrator);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 登录成功
            // 将administrator信息写入session
            administrator.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("administrator", administrator);
            // 返回成功响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(administrator);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @GetMapping("/out")
    @ResponseBody
    public JSONResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("student");
        session.removeAttribute("teacher");
        session.removeAttribute("administrator");
        return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
    }
}
