package com.example.avanscinema.JsonParsers;

import com.example.avanscinema.Classes.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenreList {

    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres = null;

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

}