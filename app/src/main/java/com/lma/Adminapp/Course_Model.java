package com.lma.Adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Course_Model implements Parcelable {
    private String name, faculty, code, source;

    public Course_Model() {}

    public Course_Model(String name, String faculty, String code, String source) {
        this.name = name;
        this.faculty = faculty;
        this.code = code;
        this.source = source;
    }

    protected Course_Model(Parcel in) {
        name = in.readString();
        faculty = in.readString();
        code = in.readString();
        source = in.readString();
    }

    public static final Creator<Course_Model> CREATOR = new Creator<Course_Model>() {
        @Override
        public Course_Model createFromParcel(Parcel in) {
            return new Course_Model(in);
        }

        @Override
        public Course_Model[] newArray(int size) {
            return new Course_Model[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(faculty);
        dest.writeString(code);
        dest.writeString(source);
    }
}