package com.dbDesign.Jacky.mapper.intermediateMapper;

import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentCourseMapperTest {
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    public void setStudentCourseMapper(StudentCourseMapper studentCourseMapper) {
        this.studentCourseMapper = studentCourseMapper;
    }

    @Test
    public void testInsertGrade(){
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(2);
        studentCourse.setCourseId(1);
        studentCourse.setUsualGrade(BigDecimal.valueOf(68));
        studentCourse.setExamGrade(BigDecimal.valueOf(76));
        studentCourseMapper.insert(studentCourse);
    }
}