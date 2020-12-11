package com.dbDesign.Jacky.common.enums;

public enum HistoryReasonEnum {

    TRANSFER_REASON(1, "转学"),
    GRADUATION_REASON(2, "毕业"),
    DROP_OUT_REASON(3, "退学");

    private Integer reasonNumber;
    private String reasonName;

    HistoryReasonEnum(Integer reasonNumber, String reasonName) {
        this.reasonNumber = reasonNumber;
        this.reasonName = reasonName;
    }

    public Integer getReasonNumber() {
        return reasonNumber;
    }

    public String getReasonName() {
        return reasonName;
    }
}
