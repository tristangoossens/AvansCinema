package com.example.avanscinema.JsonParsers;

import com.example.avanscinema.Classes.UserMovieList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListsList {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private List<UserMovieList> userMovieLists = null;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<UserMovieList> getUserMovieLists() {
        return userMovieLists;
    }

    public void setUserMovieLists(List<UserMovieList> results) {
        this.userMovieLists = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}