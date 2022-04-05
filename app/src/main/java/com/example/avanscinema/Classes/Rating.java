package com.example.avanscinema.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("value")
    @Expose
    float rating;

    public Rating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
