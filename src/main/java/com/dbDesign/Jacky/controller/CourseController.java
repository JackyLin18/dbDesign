package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.Course;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.CourseService;
import com.dbDesign.Jacky.service.TeacherService;
import com.dbDesign.Jacky.service.intermediateService.StudentCourseService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CourseController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/course")
public class CourseController {
    private CourseService courseService;
    private TeacherService teacherService;
    private StudentCourseService studentCourseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setStudentCourseService(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    /**
     * @Author Jacky
     * @Param params 需要保存的 course 的参数 JSON 字符串
     * @Description 保存 course 对象（新增/更新）
     **/
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public JSONResponse course(@RequestParam String params) throws ParseException {
        // 判断参数是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转为JSONObject
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建course对象
        Course course = new Course();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        course.setId(id);
        // 获取课程名称
        String name = paramJSONObject.getString("name");
        course.setName(name);
        // 获取教师工号
        Integer teacherId = paramJSONObject.getInteger("teacher_id");
        course.setTeacherId(teacherId);
        // 获取学时
        Integer courseHours = paramJSONObject.getInteger("course_hours");
        course.setCourseHours(courseHours);
        // 获取学分
        BigDecimal credit = paramJSONObject.getBigDecimal("credit");
        course.setCredit(credit);
        // 获取上课时间
        String classTime = paramJSONObject.getString("class_time");
        course.setClassTime(classTime);
        // 获取上课地点
        String classAddress = paramJSONObject.getString("class_address");
        course.setClassAddress(classAddress);
        // 获取考试时间
        String examTimeString = paramJSONObject.getString("exam_time");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Timestamp examTime = new Timestamp(simpleDateFormat.parse(examTimeString).getTime());
        course.setExamTime(examTime);

        ServiceResult serviceResult;
        try {
            // 保存course对象
            serviceResult = courseService.saveCourse(course);
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
            // 获取course的id
            Integer courseId = (Integer) serviceResult.getData().get("id");
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(courseId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 指定的 courseId
     * @Description 查询指定 courseId 对应的 course 数据
     **/
    @GetMapping
    @ResponseBody
    public JSONResponse course(@RequestParam Integer id, HttpServletRequest request) {
        // 判断必要参数是否为空
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定id的course数据
            serviceResult = courseService.getCourseByCourseId(id);
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
            // 获取course数据
            Course course = (Course) serviceResult.getData().get("course");
            request.getSession().setAttribute("course", course);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(course);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Description 查询所有课程
     **/
    @GetMapping(value = "/all")
    @ResponseBody
    public JSONResponse courses() {
        ServiceResult serviceResult;
        try {
            // 查询所有的course集合
            serviceResult = courseService.getAllCourseList();
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
            List<Course> courses = ListUtil.castList(
                    serviceResult.getData().get("courses"), Course.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(courses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param teacherId 需要查询的 teacher 对应的 id
     * @Description 查询出指定 teacher 授课的 course 集合
     **/
    @GetMapping(value = "/teacher")
    @ResponseBody
    public JSONResponse courseByTeacherId(@RequestParam Integer teacherId) {
        // 判断必要参数是否为空
        if (teacherId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取指定teacherId授课的course集合
            serviceResult = courseService.getCourseListByTeacherId(teacherId);
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
            List<Course> courses = ListUtil.castList(
                    serviceResult.getData().get("courses"), Course.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(courses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param optionParams 指定的查询条件
     * @Description 根据多个查询条件查询 course
     **/
    @GetMapping("/option")
    @ResponseBody
    public JSONResponse courseByOption(@RequestParam String optionParams) {
        // 判断必要参数是否为空
        if (ParamUtil.isParamNull(optionParams)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将optionParams转换为JSONObject
        JSONObject optionJSONObject = JSONObject.parseObject(optionParams);
        // 构建course对象
        Course course = new Course();
        // 获取id
        Integer id = optionJSONObject.getInteger("id");
        course.setId(id);
        // 获取课程名
        String name = optionJSONObject.getString("name");
        if (!ParamUtil.isParamNull(name)) {
            course.setName(name);
        }
        // 获取任课教师的工号
        Integer teacherId = optionJSONObject.getInteger("teacher_id");
        course.setTeacherId(teacherId);
        // 获取学时
        Integer courseHours = optionJSONObject.getInteger("course_hours");
        course.setCourseHours(courseHours);
        // 大于等于指定学时或小于等于（1为大于，2为小于，3为等于）
        Integer courseHoursType = optionJSONObject.getInteger("course_hours_type");
        if (courseHoursType == null) {
            courseHoursType = 3;
        }
        // 获取学分
        BigDecimal credit = optionJSONObject.getBigDecimal("credit");
        course.setCredit(credit);
        // 大于等于指定学分或小于等于（1为大于，2为小于，3为等于）
        Integer creditType = optionJSONObject.getInteger("credit_type");
        if (creditType == null) {
            creditType = 3;
        }
        // 获取上课时间
        String classTime = optionJSONObject.getString("class_time");
        if (!ParamUtil.isParamNull(classTime)) {
            course.setClassTime(classTime);
        }
        // 获取上课地点
        String classAddress = optionJSONObject.getString("class_address");
        if (!ParamUtil.isParamNull(classAddress)) {
            course.setClassAddress(classAddress);
        }
        // 存放teacherName对应的id集合
        List<Integer> teacherIdList = new ArrayList<>();
        // 获取教师的姓名
        String teacherName = optionJSONObject.getString("teacher_name");
        // 判断是否有指定teacher的name
        if (!ParamUtil.isParamNull(teacherName)) {
            // 从数据库中查出指定姓名的教师id集合
            ServiceResult teacherIdServiceResult;
            try {
                teacherIdServiceResult = teacherService.getTeacherIdListByName(teacherName);
            } catch (Exception ex) {
                // 捕获异常并返回失败信息
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
            // 获取状态码
            Integer resultCode = teacherIdServiceResult.getCode();
            // 判断状态码
            if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
                // 获取id集合
                teacherIdList = ListUtil.castList(teacherIdServiceResult.getData().get("idList"),
                        Integer.class);
            } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
                // 返回值为空状态
                return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
            } else {
                return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
            }

            // 判断是否有指定teacher的id
            if (teacherId != null) {
                // 判断指定的teacherId是否在teacherName对应的idList中
                if (!teacherIdList.contains(teacherId)) {
                    return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
                }
            }
        } else {
            // 没有指定teacher的name
            // 将指定的teacher存放入idList中
            if (teacherId != null) {
                teacherIdList.add(teacherId);
            }
        }

        ServiceResult serviceResult;
        try {
            // 查询满足条件的course集合
            serviceResult = courseService.getCourseListByOption(course, teacherIdList,
                    courseHoursType, creditType);
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
            // 取出course集合
            List<Course> courses = ListUtil.castList(
                    serviceResult.getData().get("courses"), Course.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(courses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 需要删除的 course 对应的 courseId
     * @Description 删除指定 id 的 course 数据
     **/
    @DeleteMapping
    @ResponseBody
    public JSONResponse deleteCourse(@RequestParam Integer id) {
        // 判断必要参数是否为空
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 删除指定id的course数据
            serviceResult = courseService.remoteCourseByCourseId(id);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 删除成功
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param studentId 指定的 student 的 id
     * @Description 根据指定的 student 获取选修的 course 集合
     **/
    @GetMapping(value = "/student")
    @ResponseBody
    public JSONResponse courseByStudentId(@RequestParam Integer studentId) {
        // 判断必要参数是否为空
        if (studentId == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 获取学生选修的course集合
            serviceResult = studentCourseService.getCourseListByStudentId(studentId);
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
            // 获取course集合
            List<Course> courses = ListUtil.castList(
                    serviceResult.getData().get("courses"), Course.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(courses);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
