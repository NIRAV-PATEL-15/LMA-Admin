package com.lma.Adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Student_Model implements Parcelable {
    private String fullname;
    private String enrollmentno;
    private String email;
    private String password;
    private String confirmpassword;
    private String dob;
    private String phone;
    private String semester;
    private String division;
    private String addstudentsID;


    public Student_Model(){

    }

    protected Student_Model(Parcel in) {
        fullname = in.readString();
        enrollmentno = in.readString();
        email = in.readString();
        password = in.readString();
        confirmpassword = in.readString();
        dob = in.readString();
        phone = in.readString();
        semester = in.readString();
        division = in.readString();
        addstudentsID = in.readString();
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

    public String getEnrollmentno() {
        return enrollmentno;
    }

    public void setEnrollmentno(String enrollmentno) {
        this.enrollmentno = enrollmentno;
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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
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

    public String getAddstudentsID() {
        return addstudentsID;
    }

    public void setAddstudentsID(String addstudentsID) {
        this.addstudentsID = addstudentsID;
    }

    public Student_Model(String fullname, String enrollmentno, String email, String password, String confirmpassword, String dob, String phone, String semester, String division, String addstudentsID) {
        this.fullname = fullname;
        this.enrollmentno = enrollmentno;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.dob = dob;
        this.phone = phone;
        this.semester = semester;
        this.division = division;
        this.addstudentsID = addstudentsID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullname);
        parcel.writeString(enrollmentno);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(confirmpassword);
        parcel.writeString(dob);
        parcel.writeString(phone);
        parcel.writeString(semester);
        parcel.writeString(division);
        parcel.writeString(addstudentsID);
    }
}
