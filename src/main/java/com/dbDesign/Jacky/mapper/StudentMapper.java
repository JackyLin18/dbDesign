package com.dbDesign.Jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select(value = "select password from student where id = #{studentId}")
    String selectPasswordByStudentId(Integer studentId);

    @Select(value = "select * from student where id = #{studentId}")
    Student selectAllMessageByStudentId(Integer studentId);

    @Select("CALL delete_student(#{studentId,mode=IN,jdbcType=INTEGER}," +
            "#{reason,mode=IN,jdbcType=INTEGER})")
    @Options(statementType= StatementType.CALLABLE)
    void deleteStudentByProcedure(Integer studentId,Integer reason);
}
