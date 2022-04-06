package com.example.avanscinema.API.RequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToListRequestBody {
    @SerializedName("media_id")
    @Expose
    private Integer movie_id;

    public AddToListRequestBody(Integer movie_id){
        this.movie_id = movie_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }
}
