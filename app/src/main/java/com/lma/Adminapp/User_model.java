package com.lma.Adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User_model implements Parcelable {
  private   String fullname,username,email, pass,gender,dob,cno,graduation;
  public User_model(){

  }

    public User_model(String fullname, String username, String email, String  pass, String gender, String dob, String cno, String graduation) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this. pass =  pass;
        this.gender = gender;
        this.dob = dob;
        this.cno = cno;
        this.graduation = graduation;

    }

    protected User_model(Parcel in) {
        fullname = in.readString();
        username = in.readString();
        email = in.readString();
         pass = in.readString();
        gender = in.readString();
        dob = in.readString();
        cno = in.readString();
        graduation = in.readString();

    }

    public static final Creator<User_model> CREATOR = new Creator<User_model>() {
        @Override
        public User_model createFromParcel(Parcel in) {
            return new User_model(in);
        }

        @Override
        public User_model[] newArray(int size) {
            return new User_model[size];
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
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
        dest.writeString( pass);
        dest.writeString(gender);
        dest.writeString(dob);
        dest.writeString(cno);
        dest.writeString(graduation);

    }
}