package com.example.avanscinema.API;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.MovieDetail;
import com.example.avanscinema.Classes.MovieList;

import java.util.ArrayList;

public interface responseListener {
    public void getMoviePopularList(ArrayList<Movie> result);
    public void getDetails(MovieDetail movie);
}
