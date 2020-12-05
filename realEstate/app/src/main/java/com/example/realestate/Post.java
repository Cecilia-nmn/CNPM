package com.example.realestate;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable {
    @SerializedName("postId")
    private int postId;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("address")
    private String address;
    @SerializedName("area")
    private String area;
    @SerializedName("size")
    private String size;
    @SerializedName("rooms")
    private String rooms;
    @SerializedName("descript")
    private String descript;
    @SerializedName("username")
    private String username;

    public Post(String title, String image, String address, String area, String size, String rooms, String descript, String username) {
        this.title = title;
        this.image = image;
        this.address = address;
        this.area = area;
        this.size = size;
        this.rooms = rooms;
        this.descript = descript;
        this.username = username;
    }

    public Post(int postId, String title, String image, String address, String area, String size, String rooms, String descript, String username) {
        this.postId = postId;
        this.title = title;
        this.image = image;
        this.address = address;
        this.area = area;
        this.size = size;
        this.rooms = rooms;
        this.descript = descript;
        this.username = username;
    }
    protected Post(Parcel in) {
        postId = in.readInt();
        title = in.readString();
        image = in.readString();
        address = in.readString();
        area = in.readString();
        size = in.readString();
        rooms = in.readString();
        descript = in.readString();
        username = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postId);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(size);
        dest.writeString(rooms);
        dest.writeString(descript);
        dest.writeString(username);
    }
}
