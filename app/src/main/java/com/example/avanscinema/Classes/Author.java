package com.example.avanscinema.Classes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("avatar_path")
    @Expose
    private Object avatarPath;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(Object avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}