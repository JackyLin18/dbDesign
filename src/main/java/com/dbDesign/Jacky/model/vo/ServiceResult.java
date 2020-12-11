package com.dbDesign.Jacky.model.vo;

import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.util.ParamUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ServiceResult
 * @Author Jacky
 * @Description 封装 Service 层返回数据的实体类
 **/
@Data
public class ServiceResult {
    private final static CodeEnum SUCCESS_CODE = CodeEnum.SUCCESS;
    private final static CodeEnum FAIL_CODE = CodeEnum.FAIL;
    private final static CodeEnum DATABASE_ERROR_CODE = CodeEnum.DATABASE_ERROR;
    private final static CodeEnum PARAMETER_MISSING_CODE = CodeEnum.PARAMETER_MISSING;

    // 是否执行成功
    private Boolean result;
    // 返回码
    private Integer code;
    // 返回信息
    private String message;
    // 返回数据
    private Map<String, Object> data;

    // 禁用构造方法
    private ServiceResult() {
    }

    public static ServiceResult ok(String dataName, Object dataValue) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(true)
                .setCode(SUCCESS_CODE.getCode())
                .setMessage(SUCCESS_CODE.getMessage());
        if (!ParamUtil.isParamNull(dataName)) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(dataName, dataValue);
            serviceResult.setData(dataMap);
        }
        return serviceResult;
    }

    public static ServiceResult ok() {
        return ok(null, null);
    }

    public static ServiceResult fail() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(false)
                .setCode(FAIL_CODE.getCode())
                .setMessage(FAIL_CODE.getMessage());
        return serviceResult;
    }

    public static ServiceResult fail(CodeEnum codeEnum) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(false).setCode(codeEnum.getCode())
                .setMessage(codeEnum.getMessage());
        return serviceResult;
    }

    public static ServiceResult error() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(false)
                .setCode(DATABASE_ERROR_CODE.getCode())
                .setMessage(DATABASE_ERROR_CODE.getMessage());
        return serviceResult;
    }

    public static ServiceResult paramMissing() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(false)
                .setCode(PARAMETER_MISSING_CODE.getCode())
                .setMessage(PARAMETER_MISSING_CODE.getMessage());
        return serviceResult;
    }

    public ServiceResult setResult(boolean result) {
        this.result = result;
        return this;
    }

    public ServiceResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ServiceResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceResult setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}
