package com.example.avanscinema.API;

import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ResponseListener {
    public void getMoviePopularList(ArrayList<Movie> result);
    public void getDetails(Movie movie, ReviewList reviews, CastList cast, TrailerList trailer);
    public void searchMovie(ArrayList<Movie> result);
}
