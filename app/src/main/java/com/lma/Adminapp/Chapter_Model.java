package com.lma.Adminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapter_Model implements Parcelable {
    private String Title,url;

    public Chapter_Model() {}

    public Chapter_Model(String title, String url) {
        this.Title = title;
        this.url = url;
    }

    protected Chapter_Model(Parcel in) {
        Title = in.readString();
        url = in.readString();
    }

    public static final Creator<Chapter_Model> CREATOR = new Creator<Chapter_Model>() {
        @Override
        public Chapter_Model createFromParcel(Parcel in) {
            return new Chapter_Model(in);
        }

        @Override
        public Chapter_Model[] newArray(int size) {
            return new Chapter_Model[size];
        }
    };

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(url);
    }
}
