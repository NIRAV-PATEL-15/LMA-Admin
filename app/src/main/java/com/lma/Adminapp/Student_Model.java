package com.lma.Adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Student_Model implements Parcelable {
    private String fullname;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String dob;
    private String phone;
    private String semester;
    private String division;
    private String branch;


    public Student_Model() {
    }

    public Student_Model(String fullname, String username, String email, String password, String gender, String dob, String phone, String semester, String division, String branch) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.semester = semester;
        this.division = division;
        this.branch = branch;
    }

    protected Student_Model(Parcel in) {
        fullname = in.readString();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        gender = in.readString();
        dob = in.readString();
        phone = in.readString();
        semester = in.readString();
        division = in.readString();
        branch = in.readString();
    }

    public static final Creator<Student_Model> CREATOR = new Creator<Student_Model>() {
        @Override
        public Student_Model createFromParcel(Parcel in) {
            return new Student_Model(in);
        }

        @Override
        public Student_Model[] newArray(int size) {
            return new Student_Model[size];
        }
    };

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullname);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(gender);
        dest.writeString(dob);
        dest.writeString(phone);
        dest.writeString(semester);
        dest.writeString(division);
        dest.writeString(branch);
    }
}