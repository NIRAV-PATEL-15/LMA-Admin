package com.lma.Adminapp;

public class DataHolder {
    String fname,uname,emailadd,password,gender,dob,phone,grad;

    public DataHolder(String fname, String uname, String emailadd, String password, String gender, String dob, String phone, String grad) {
        this.fname = fname;
        this.uname = uname;
        this.emailadd = emailadd;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.grad = grad;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmailadd() {
        return emailadd;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }
}
