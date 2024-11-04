package com.majorproject.CareerforIT.DTO;

public class Register {
    private String name;
    private String email;
    private String password;
    private int year;
    private String branch;
    private int phn_no;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getPhn_no() {
        return phn_no;
    }

    public void setPhn_no(int phn_no) {
        this.phn_no = phn_no;
    }


}
