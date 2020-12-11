package com.dbDesign.Jacky.service.impl;

import com.dbDesign.Jacky.model.entity.Administrator;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.AdministratorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdministratorServiceImplTest {
    private AdministratorService administratorService;

    @Autowired
    public void setAdministratorService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Test
    void saveAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setPassword("123455");
        ServiceResult serviceResult = administratorService.saveAdministrator(administrator);
        System.out.println(serviceResult);
    }
}