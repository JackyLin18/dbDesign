package com.dbDesign.Jacky.mapper.viewMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dbDesign.Jacky.model.entity.view.GradeReportView;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GradeReportViewMapper extends BaseMapper<GradeReportView> {
}
