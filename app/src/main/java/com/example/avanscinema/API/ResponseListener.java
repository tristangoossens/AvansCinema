package com.example.avanscinema.API;

import com.example.avanscinema.Classes.Movie;

import java.util.ArrayList;

public interface ResponseListener {
    public void getMoviePopularList(ArrayList<Movie> result);
    public void getDetails(Movie movie);
//    public void searchMovie(ArrayList<Movie> result);
}
