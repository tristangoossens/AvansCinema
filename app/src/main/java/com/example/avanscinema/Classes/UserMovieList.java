package com.example.avanscinema.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserMovieList {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("favorite_count")
    @Expose
    private Integer favoriteCount;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("item_count")
    @Expose
    private Integer itemCount;

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @SerializedName("list_type")
    @Expose
    private String listType;

    @SerializedName("items")
    @Expose
    private List<Movie> movies = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}