package com.dbDesign.Jacky.controller;

import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.common.enums.JSONResponseEnum;
import com.dbDesign.Jacky.model.dto.JSONResponse;
import com.dbDesign.Jacky.model.entity.StudentHistory;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.StudentHistoryService;
import com.dbDesign.Jacky.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName StudentHistoryController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/studentHistory")
public class StudentHistoryController {
    private StudentHistoryService studentHistoryService;

    @Autowired
    public void setStudentHistoryService(StudentHistoryService studentHistoryService) {
        this.studentHistoryService = studentHistoryService;
    }

    @GetMapping("/all")
    @ResponseBody
    public JSONResponse allStudentHistory() {
        ServiceResult serviceResult;
        try {
            // 获取所有历史记录
            serviceResult = studentHistoryService.getAllStudentHistory();
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
            // 获取数据集合
            List<StudentHistory> studentHistories = ListUtil.castList(
                    serviceResult.getData().get("studentHistories"), StudentHistory.class);
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(studentHistories);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
