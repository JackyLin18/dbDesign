package com.dbDesign.Jacky.service.impl;

import com.dbDesign.Jacky.model.entity.Teacher;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherServiceImplTest {
    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void saveTeacher() throws ParseException {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("刁毛");
        teacher.setSex(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        teacher.setBirthday(new Date(simpleDateFormat.parse("1985-10-1").getTime()));
        teacher.setDepartmentId(1);
        teacher.setTitle("废物");
        teacher.setMajor("软件工程");
        teacher.setTeachingDirection("好好学习");
        teacherService.saveTeacher(teacher);
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void getTeacherByTeacherId() {
        ServiceResult serviceResult = teacherService.getTeacherByTeacherId(1);
        System.out.println(serviceResult);
    }

    @Test
    void getTeacherByCourseId() {
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void getTeachersByDepartmentId() {
        ServiceResult serviceResult = teacherService.getTeachersByDepartmentId(1);
        System.out.println(serviceResult);
    }

    /** 
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void remoteTeacherByTeacherId() {
        ServiceResult serviceResult = teacherService.remoteTeacherByTeacherId(1);
        System.out.println(serviceResult);
    }
}