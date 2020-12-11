package com.dbDesign.Jacky.common.enums;


import com.dbDesign.Jacky.model.dto.JSONResponse;

public enum JSONResponseEnum {
    FAIL_RESPONSE("fail", new JSONResponse().fail(CodeEnum.FAIL)),
    DATABASE_ERROR_RESPONSE("database error", new JSONResponse().fail(CodeEnum.DATABASE_ERROR)),
    SUCCESS_RESPONSE("success", new JSONResponse().success()),
    SUCCESS_WITHOUT_DATA_RESPONSE("success without data return", new JSONResponse().success()),
    NULL_RESULT_RESPONSE("null result",
            new JSONResponse().fail(CodeEnum.NULL_RESULT)),
    WITHOUT_AUTHORITY_RESPONSE("without authority",
            new JSONResponse().fail(CodeEnum.WITHOUT_AUTHORITY)),
    PARAMETER_MISSING_RESPONSE("parameter missing",
            new JSONResponse().fail(CodeEnum.PARAMETER_MISSING)),
    PARAMETER_VALUE_INVALID_RESPONSE("parameter value invalid",
            new JSONResponse().fail(CodeEnum.PARAMETER_VALUE_INVALID)),
    NULL_PARAM_RESPONSE("param is null",
            new JSONResponse().fail(CodeEnum.NULL_PARAM)),
    OTHER_ERROR_RESPONSE("other error",
            new JSONResponse().fail(CodeEnum.OTHER_ERROR));

    private String responseName;
    private JSONResponse responseValue;

    JSONResponseEnum(String responseName, JSONResponse responseValue) {
        this.responseName = responseName;
        this.responseValue = responseValue;
    }

    public String getResponseName() {
        return responseName;
    }

    public JSONResponse getResponseValue() {
        return responseValue;
    }
}
