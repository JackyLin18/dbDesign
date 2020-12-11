package com.dbDesign.Jacky.service.impl;

import com.dbDesign.Jacky.mapper.DepartmentMapper;
import com.dbDesign.Jacky.model.entity.Department;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DepartmentServiceImplTest {
    private DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void saveDepartment() {
        Department department = new Department("土木", "踢球很不错");
//        Department department = new Department(2);
//        department.setIntroduction("很有精神");
        ServiceResult serviceResult = departmentService.saveDepartment(department);
        System.out.println(serviceResult);
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void getDepartmentByDepartmentId() {
        ServiceResult serviceResult = departmentService.getDepartmentByDepartmentId(2);
        System.out.println(serviceResult);
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void getDepartmentByStudentId() {
        ServiceResult serviceResult = departmentService.getDepartmentByStudentId(2);
        System.out.println(serviceResult);
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void getDepartmentByTeacherId() {
        ServiceResult serviceResult = departmentService.getDepartmentByTeacherId(1);
        System.out.println(serviceResult);
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void remoteDepartmentById() {
        ServiceResult serviceResult = departmentService.remoteDepartmentById(1);
        System.out.println(serviceResult);
    }

    @Test
    void getDepartmentList() {
    }
}