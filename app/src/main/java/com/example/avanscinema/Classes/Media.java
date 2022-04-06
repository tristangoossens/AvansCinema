package com.example.avanscinema.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {
    @SerializedName("media_type")
    @Expose
    private String type;
    @SerializedName("media_id")
    @Expose
    private int id;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public Media(String type, int Id, boolean favorite) {
        this.type = type;
        this.id = Id;
        this.favorite = favorite;
    }

    public String getType() {
        return this.type;
    }
    public int getId() {
        return this.id;
    }
    public boolean getFavorite() {
        return this.favorite;
    }
}
