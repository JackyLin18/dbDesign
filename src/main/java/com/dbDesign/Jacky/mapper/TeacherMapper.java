package com.dbDesign.Jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select(value = "select password from teacher_5062 where id = #{teacherId}")
    String selectPasswordByTeacherId(Integer teacherId);
}
