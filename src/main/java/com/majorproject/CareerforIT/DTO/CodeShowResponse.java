package com.majorproject.CareerforIT.DTO;

public class CodeShowResponse {
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setQuesid(int quesid) {
        this.quesid = quesid;
    }

    private String lang;
    private String result;
    private int quesid;
}
