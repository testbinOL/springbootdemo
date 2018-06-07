package com.bin.demo.vo;

public class Param {
    String methodName;
    String value;

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "methodName='" + methodName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
