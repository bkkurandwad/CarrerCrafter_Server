package com.majorproject.CareerforIT.DTO;

public class AssesmentResponse {
    public int id;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(int totalQues) {
        this.totalQues = totalQues;
    }

    public String name;
    public String desc;
    public int totalQues;
}
