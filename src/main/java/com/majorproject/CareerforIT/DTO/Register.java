package com.majorproject.CareerforIT.DTO;

public class Register {
    private String name;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
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

    public int getPhn_no() {
        return phn_no;
    }

    public void setPhn_no(int phn_no) {
        this.phn_no = phn_no;
    }


}
