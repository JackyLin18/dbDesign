package com.dbDesign.Jacky.mapper.viewMapper;

import com.dbDesign.Jacky.model.entity.view.GradeView;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class GradeViewMapperTest {
    private GradeViewMapper gradeViewMapper;

    @Autowired
    public void setGradeViewMapper(GradeViewMapper gradeViewMapper) {
        this.gradeViewMapper = gradeViewMapper;
    }

    @Test
    void testSelectAll(){
        List<GradeView> gradeViews = gradeViewMapper.selectList(null);
        gradeViews.forEach(System.out::println);
    }
}