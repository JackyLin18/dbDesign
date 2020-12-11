package com.dbDesign.Jacky.service.impl;

import com.dbDesign.Jacky.common.enums.CodeEnum;
import com.dbDesign.Jacky.mapper.AdministratorMapper;
import com.dbDesign.Jacky.model.entity.Administrator;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.AdministratorService;
import com.dbDesign.Jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @ClassName AdministratorServiceImpl
 * @Author Jacky
 * @Description
 **/
@Service("administratorService")
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    private AdministratorMapper administratorMapper;

    @Autowired
    public void setAdministratorMapper(AdministratorMapper administratorMapper) {
        this.administratorMapper = administratorMapper;
    }

    @Override
    public ServiceResult saveAdministrator(Administrator administrator) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (administrator.getId() == null) {
            int insert = administratorMapper.insert(administrator);
            if (insert > 0) {
                resultMap.put("insert", insert);
                resultMap.put("id", administrator.getId());
                return ServiceResult.ok().setData(resultMap);
            } else {
                return ServiceResult.fail();
            }
        } else {
            int update = administratorMapper.updateById(administrator);
            if (update > 0) {
                resultMap.put("update", update);
                resultMap.put("id", administrator.getId());
                return ServiceResult.ok().setData(resultMap);
            } else {
                return ServiceResult.fail(CodeEnum.NULL_RESULT);
            }
        }
    }

    @Override
    public ServiceResult loginAdministrator(Administrator administrator) {
        // 获得id
        Integer id = administrator.getId();
        // 获得输入的密码
        String inputPassword = administrator.getPassword();
        // 输入参数判断是否为空
        if (id == null || ParamUtil.isParamNull(inputPassword)) {
            return ServiceResult.fail(CodeEnum.PARAMETER_MISSING);
        }
        // 获得正确的密码
        String rightPassword = administratorMapper.selectPasswordByAdministratorId(id);
        // inputPassword与rightPassword比较
        if (rightPassword.equals(inputPassword)) {
            // 密码正确
            return ServiceResult.ok();
        }
        // 密码错误
        return ServiceResult.fail();
    }
}
