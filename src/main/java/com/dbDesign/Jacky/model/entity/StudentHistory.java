package com.dbDesign.Jacky.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    // 原因（0为未知，1为转学，2为毕业，3为退学）
    private Integer reason;
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

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
