package com.example.realestate;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Report implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("content")
    private String content;

    public Report(String email, String content) {
        this.email = email;
        this.content = content;
    }


    protected Report(Parcel in) {
        id = in.readInt();
        email = in.readString();
        content = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Creator<Report> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(content);
    }

}
