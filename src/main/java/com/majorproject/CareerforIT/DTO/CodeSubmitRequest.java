package com.majorproject.CareerforIT.DTO;

public class CodeSubmitRequest {
    private String code;

    public String getResult() {
        return result;
    }

    public int getQuesid() {
        return quesid;
    }

    public String getLang() {
        return lang;
    }

    public String getCode() {
        return code;
    }

    private String lang;
    private String result;
    private int quesid;
}
