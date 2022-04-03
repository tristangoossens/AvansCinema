package com.example.avanscinema.JsonParsers;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.avanscinema.Activities.TrailerPage;
import com.example.avanscinema.Classes.Trailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private ArrayList<Trailer> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}