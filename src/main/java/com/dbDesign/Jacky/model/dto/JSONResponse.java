package com.dbDesign.Jacky.model.dto;

import com.dbDesign.Jacky.common.enums.CodeEnum;
import lombok.Data;

/**
 * @ClassName JSONResponse
 * @Description 后台接口返回结果的实体类
 */
@Data
public class JSONResponse {

    private Boolean status; // true->成功 false->失败
    private Integer code;   // 错误码，具体看接口文档的定义，和CodeEnum类
    private String message; // 提示信息
    private String error;   // 错误信息，开发时用
    private Object data;    // 携带数据
    private String timestamp = String.valueOf(System.currentTimeMillis());  // 返回后台时间戳

    public JSONResponse() {
    }

    public JSONResponse(Boolean status, Integer code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JSONResponse success() {
        this.status = true;
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = CodeEnum.SUCCESS.getMessage();
        return this;
    }

    public JSONResponse success(String message) {
        this.status = true;
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = message;
        return this;
    }

    public JSONResponse fail(CodeEnum codeEnum) {
        this.status = false;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        return this;
    }

    public JSONResponse fail(CodeEnum codeEnum, String message) {
        this.status = false;
        this.code = codeEnum.getCode();
        this.message = message;
        return this;
    }

    public JSONResponse fail(Integer code, String message) {
        this.status = false;
        this.code = code;
        this.message = message;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public JSONResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public JSONResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JSONResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getError() {
        return error;
    }

    public JSONResponse setError(String error) {
        this.error = error;
        return this;
    }

}
