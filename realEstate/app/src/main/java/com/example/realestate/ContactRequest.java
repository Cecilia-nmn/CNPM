package com.example.realestate;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ContactRequest implements Parcelable {
    @SerializedName("crId")
    private int id;
    @SerializedName("senderId")
    private String username;
    @SerializedName("receiverId")
    private String receiverId;
    @SerializedName("contactInf")
    private String contacInf;

    public ContactRequest(int id, String username, String receiverId, String contacInf) {
        this.id = id;
        this.username = username;
        this.receiverId = receiverId;
        this.contacInf = contacInf;
    }

    public ContactRequest(String username, String receiverId, String contacInf) {
        this.username = username;
        this.receiverId = receiverId;
        this.contacInf = contacInf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContacInf() {
        return contacInf;
    }

    public void setContacInf(String contacInf) {
        this.contacInf = contacInf;
    }

    public static Creator<ContactRequest> getCREATOR() {
        return CREATOR;
    }

    protected ContactRequest(Parcel in) {
        id = in.readInt();
        username = in.readString();
        receiverId = in.readString();
        contacInf = in.readString();
    }

    public static final Creator<ContactRequest> CREATOR = new Creator<ContactRequest>() {
        @Override
        public ContactRequest createFromParcel(Parcel in) {
            return new ContactRequest(in);
        }

        @Override
        public ContactRequest[] newArray(int size) {
            return new ContactRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(receiverId);
        dest.writeString(contacInf);
    }
}
