package com.example.realestate;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("phone")
    private String phone;
    @SerializedName("role")
    private int role;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = "";
        this.role = 1;
    }

    public User(String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = "";
        this.role = 1;
    }

    public User(String username, String password, String email, String phone, int role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.role = role;
    }

    public User(String username, String password, String email,  int role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = "";
        this.role = role;
    }


    protected User(Parcel in) {
        email = in.readString();
        username = in.readString();
        password = in.readString();
        phone = in.readString();
        role = in.readInt();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeInt(role);
    }
}
