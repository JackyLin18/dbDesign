package com.dbDesign.Jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
    @Select(value = "select password from administrator where id = #{administratorId}")
    String selectPasswordByAdministratorId(Integer administratorId);
}
