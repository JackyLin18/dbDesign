package com.dbDesign.Jacky.service;

import com.dbDesign.Jacky.model.entity.Administrator;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface AdministratorService {
    ServiceResult saveAdministrator(Administrator administrator);

    ServiceResult loginAdministrator(Administrator administrator);
}
