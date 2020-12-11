package com.dbDesign.Jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    /**
     * @Author Jacky
     * @Param department 需要逻辑删除的系别
     * @Description 逻辑删除指定的 department
     **/
    void deleteByIdWithFill(Department department);
}
