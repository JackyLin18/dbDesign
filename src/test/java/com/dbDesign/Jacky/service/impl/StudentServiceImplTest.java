package com.dbDesign.Jacky.service.impl;

import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.vo.ServiceResult;
import com.dbDesign.Jacky.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceImplTest {
    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @Author Jacky
     * @Description 测试通过
     **/
    @Test
    void saveStudent() throws ParseException {
        Student student = new Student();
//        student.setName("李四");
//        student.setSex(2);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        student.setBirthday(new Date(simpleDateFormat.parse("2001-10-03").getTime()));
//        student.setEnrolledScore(622);
//        student.setDepartmentId(1);
        student.setId(1);
        student.setName("张三");
        student.setSex(1);
        ServiceResult serviceResult = studentService.saveStudent(student);
        System.out.println(serviceResult);
    }

    @Test
    void getStudentByStudentId() {
        ServiceResult serviceResult = studentService.getStudentByStudentId(1);
        System.out.println(serviceResult);
    }

    @Test
    void getStudentListByDepartmentId() {
    }

    @Test
    void getStudentListByCourseId() {
    }
}