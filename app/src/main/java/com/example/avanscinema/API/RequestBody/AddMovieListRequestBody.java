package com.example.avanscinema.API.RequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMovieListRequestBody {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    public AddMovieListRequestBody(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
