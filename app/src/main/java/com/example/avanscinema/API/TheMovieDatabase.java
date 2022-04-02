package com.example.avanscinema.API;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabase {

    //Call voor ophalen van popular filmlijsten.
    @GET("popular")
    Call<MovieList> listMovies(@Query("api_key") String api_key);

    //Call voor film Details van geselecteerde film.
    @GET("{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String api_key);
}
