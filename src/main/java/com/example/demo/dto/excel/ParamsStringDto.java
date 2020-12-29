package com.example.demo.dto.excel;

public class ParamsStringDto {

    /**
     * 下拉框的键
     */
    private String value;

    /**
     * 下拉框显示值
     */
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
