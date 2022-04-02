package com.example.avanscinema.JsonParsers;

import java.io.Serializable;
import java.util.List;

import com.example.avanscinema.Classes.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

}