package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName StudentHistory
 * @Author Jacky
 * @Description
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentHistory extends Student {
    @TableId(type = IdType.INPUT)
    private Integer id;
    // 原因（0为未知，1为转学，2为毕业，3为退学）
    private Integer reason;

    public StudentHistory() {

    }

    public StudentHistory(Integer id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        return super.toString() + "StudentHistory{" +
                "reason=" + reason +
                '}';
    }
}
