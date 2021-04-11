package com.atguigu.orderservice.entity.status;

import com.atguigu.commonutlis.utils.ResultCode;

public enum  OrderPayStatus {

    WAIT(25000,"WAIT"),
    SUCCESS(26000,"SUCCESS"),
    ERROR(27000,"ERROR");

    private int code;

    private String label;

    OrderPayStatus(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    private void setLabel(String label) {
        this.label = label;
    }
}
