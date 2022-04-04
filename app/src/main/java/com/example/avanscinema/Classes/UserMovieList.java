package com.example.avanscinema.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserMovieList {

    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("favorite_count")
    @Expose
    private Integer favoriteCount;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("items")
    @Expose
    private List<UserMovieListItem> items = null;
    @SerializedName("item_count")
    @Expose
    private Integer itemCount;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;


    public UserMovieList(String createdBy, String description, Integer favoriteCount, String id, List<UserMovieListItem> items, Integer itemCount, String iso6391, String name, String posterPath) {
        super();
        this.createdBy = createdBy;
        this.description = description;
        this.favoriteCount = favoriteCount;
        this.id = id;
        this.items = items;
        this.itemCount = itemCount;
        this.iso6391 = iso6391;
        this.name = name;
        this.posterPath = posterPath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserMovieListItem> getItems() {
        return items;
    }

    public void setItems(List<UserMovieListItem> items) {
        this.items = items;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}