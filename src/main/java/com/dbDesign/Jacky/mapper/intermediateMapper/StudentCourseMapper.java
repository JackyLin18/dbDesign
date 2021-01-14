package com.dbDesign.Jacky.mapper.intermediateMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.intermediate.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
    @Select(value = "select student_id from student_course_5062 where course_id = #{courseId}")
    List<Integer> selectStudentIdListByCourseId(Integer courseId);

    @Select(value = "select course_id from student_course_5062 where student_id = #{studentId}")
    List<Integer> selectCourseIdListByStudentId(Integer studentId);

    @Select(value = "SELECT SUM(credit) FROM course_5062 WHERE id IN ( " +
            "  SELECT course_id FROM student_course_5062 WHERE student_id = #{studentId})")
    Double selectCreditCountByStudentId(Integer studentId);
}
