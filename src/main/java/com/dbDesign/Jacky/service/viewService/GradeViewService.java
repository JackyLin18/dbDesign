package com.dbDesign.Jacky.service.viewService;

import com.dbDesign.Jacky.model.entity.view.GradeView;
import com.dbDesign.Jacky.model.vo.ServiceResult;

public interface GradeViewService {
    ServiceResult getAllGrade();

    ServiceResult getGradesByOption(GradeView gradeView);
}
