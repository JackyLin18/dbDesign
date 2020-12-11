package com.dbDesign.Jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.Department;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.DepartmentService;
import com.dbDesign.Jacky.util.ListUtil;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName DepartmentController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/department")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * @Author Jacky
     * @Param params 需要保存的 department 的 JSON 字符串信息
     * @Description 保存 department 对象（新增/更新）
     **/
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public JSONResponse department(@RequestParam String params) {
        // 判断参数JSON字符串是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建department对象
        Department department = new Department();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        department.setId(id);
        // 获取系名称
        String name = paramJSONObject.getString("name");
        department.setName(name);
        // 获取系介绍
        String introduction = paramJSONObject.getString("introduction");
        department.setIntroduction(introduction);

        ServiceResult serviceResult;
        try {
            // 保存department对象
            serviceResult = departmentService.saveDepartment(department);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功返回成功状态
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回状态为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 发生其它错误
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 指定的 departmentId
     * @Description 查询指定 id 的 department 数据
     **/
    @GetMapping
    @ResponseBody
    public JSONResponse department(@RequestParam Integer id) {
        // 判断必要参数是否为空
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = departmentService.getDepartmentByDepartmentId(id);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 获取department数据
            Department department = (Department) serviceResult.getData().get("department");
            // 返回成功状态
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(department);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回结果为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Param id 指定删除的 departmentId
     * @Description 逻辑删除指定的 department
     **/
    @DeleteMapping
    @ResponseBody
    public JSONResponse deleteDepartment(@RequestParam Integer id) {
        // 判断必要参数
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 删除指定id的department
            serviceResult = departmentService.remoteDepartmentById(id);
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
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * @Author Jacky
     * @Description 获取所有的 department 集合
     **/
    @GetMapping("/all")
    @ResponseBody
    public JSONResponse departments(HttpServletRequest request) {
        ServiceResult serviceResult;
        try {
            // 获取department集合
            serviceResult = departmentService.getDepartmentList();
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 获取成功
            List<Department> departments = ListUtil.castList(
                    serviceResult.getData().get("departments"), Department.class);
            request.getSession().setAttribute("departments",departments);
            // 返回成功状态
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(departments);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
