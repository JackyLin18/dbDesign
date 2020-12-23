package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.StudentService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName StudentController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @Author Jacky
     * @Param params 请求参数对应的 JSON 字符串
     * @Description 保存 student 对象（新增/更新）
     **/
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public JSONResponse student(@RequestParam String params, HttpServletRequest request)
            throws ParseException {
        // 参数JSON判断是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建student对象
        Student student = new Student();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        student.setId(id);
        // 获取登录密码
        String password = paramJSONObject.getString("password");
        student.setPassword(password);
        // 获取name
        String name = paramJSONObject.getString("name");
        student.setName(name);
        // 获取sex
        Integer sex = paramJSONObject.getInteger("sex");
        student.setSex(sex);
        // 获取birthday
        String birthday = paramJSONObject.getString("birthday");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setBirthday(new Date(simpleDateFormat.parse(birthday).getTime()));
        // 获取入学成绩
        Integer enrolledScore = paramJSONObject.getInteger("enrolled_score");
        student.setEnrolledScore(enrolledScore);
        // 获取专业id
        Integer departmentId = paramJSONObject.getInteger("department_id");
        student.setDepartmentId(departmentId);
        if(departmentId == null){
            student.setDepartmentId(0);
        }

        ServiceResult serviceResult;
        try {
            // 保存student对象
            serviceResult = studentService.saveStudent(student);
        } catch (Exception ex) {
            // 捕获异常返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态
            // 获取更改的student对象的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
            // 判断是否为更新操作
            Integer update = (Integer) serviceResult.getData().get("update");
            if (update != null) {
                // 将修改的student信息存入session
                request.getSession().setAttribute("student", student);
            }
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(resultId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 返回失败状态
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 需要获取的 student 的 id
     * @Description 根据指定 id 获取对应的 student 信息
     **/
    @GetMapping
    @ResponseBody
    public JSONResponse student(@RequestParam Integer id) {
        // 检查是否携带必要参数
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 查询指定id的student数据
            serviceResult = studentService.getStudentByStudentId(id);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 状态码为成功
            // 获取返回结果中的student数据
            Student student = (Student) serviceResult.getData().get("student");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(student);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 状态码为返回值为空
            // 返回响应
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 发送其它错误
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Description 查询所有学生
     **/
    @GetMapping("/all")
    @ResponseBody
    public JSONResponse allStudents() {
        ServiceResult serviceResult;
        try {
            // 获取所有student
            serviceResult = studentService.getAllStudentList();
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
            // 获取student集合
            List<Student> students = ListUtil.castList(
                    serviceResult.getData().get("students"), Student.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(students);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param departmentId 指定的 departmentId
     * @Description 查询出指定 departmentId 的 student 集合
     **/
    @GetMapping(value = "/department")
    @ResponseBody
    public JSONResponse studentByDepartmentId(@RequestParam Integer departmentId) {
        // 判断必要参数是否为空
        if (departmentId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 查询指定departmentId的student集合
            serviceResult = studentService.getStudentListByDepartmentId(departmentId);
        } catch (Exception ex) {
            // 捕获异常返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态，获取student集合
            List<Student> students = ListUtil.castList
                    (serviceResult.getData().get("students"), Student.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(students);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param courseId 指定的 courseId
     * @Description 查询出指定 courseId 的 student 集合
     **/
    @GetMapping(value = "/course")
    @ResponseBody
    public JSONResponse studentByCourseId(@RequestParam Integer courseId) {
        // 判断必要参数是否为空
        if (courseId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 查询指定courseId的student集合
            serviceResult = studentService.getStudentListByCourseId(courseId);
        } catch (Exception ex) {
            // 捕获异常返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态，获取student集合
            List<Student> students = ListUtil.castList
                    (serviceResult.getData().get("students"), Student.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(students);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param optionParams 指定的查询条件
     * @Description 根据多个查询条件查询 student
     **/
    @GetMapping("/option")
    @ResponseBody
    public JSONResponse studentByOption(@RequestParam String optionParams) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(optionParams)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将optionParams转换为JSONObject
        JSONObject optionJSONObject = JSONObject.parseObject(optionParams);
        // 构建student对象
        Student student = new Student();
        // 获取id
        Integer id = optionJSONObject.getInteger("id");
        student.setId(id);
        // 获取姓名
        String name = optionJSONObject.getString("name");
        if (!ParamUtil.isParamNull(name)) {
            student.setName(name);
        }
        // 获取入学成绩
        Integer enrolledScore = optionJSONObject.getInteger("enrolled_score");
        student.setEnrolledScore(enrolledScore);
        // 大于等于指定成绩或小于等于（1为大于，2为小于，3为等于）
        Integer scoreType = optionJSONObject.getInteger("score_type");
        if (scoreType == null) {
            scoreType = 3;
        }
        // 获取系别
        Integer departmentId = optionJSONObject.getInteger("department_id");
        student.setDepartmentId(departmentId);

        ServiceResult serviceResult;
        try {
            // 查询指定条件的student集合
            serviceResult = studentService.getStudentListByOption(student, scoreType);
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
            // 获取student集合
            List<Student> students = ListUtil.castList(
                    serviceResult.getData().get("students"), Student.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(students);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @DeleteMapping
    @ResponseBody
    public JSONResponse deleteStudent(@RequestParam String params) {
        // 判断必要参数是否为空
        if (params == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将params转为JSONObject
        JSONObject paramsJSONObject = JSONObject.parseObject(params);
        // 获取id
        Integer id = paramsJSONObject.getInteger("id");
        // 获取reason
        Integer reason = paramsJSONObject.getInteger("reason");
        ServiceResult serviceResult;
        try {
            serviceResult = studentService.remoteStudentByStudentId(id, reason);
        } catch (Exception ex) {
            // 捕获异常返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else {
            // 失败状态
            return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
        }
    }
}
