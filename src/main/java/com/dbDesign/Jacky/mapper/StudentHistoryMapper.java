package com.dbDesign.Jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.Department;
import com.dbDesign.Jacky.model.entity.StudentHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName StudentHistoryMapper
 * @Author Jacky
 * @Description
 **/
@Repository
@Mapper
public interface StudentHistoryMapper extends BaseMapper<StudentHistory> {
//    /**
//     * @Author Jacky
//     * @Param studentHistory 需要逻辑删除的学生历史记录
//     * @Description 逻辑删除指定的 studentHistory
//     **/
//    void deleteByIdWithFill(StudentHistory studentHistory);
}
