package com.example.avanscinema.API;

import com.example.avanscinema.Classes.MovieDetail;
import com.example.avanscinema.Classes.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabase {


    @GET("popular")
    Call<MovieList> listMovies(@Query("api_key") String api_key);

    @GET("{id}")
    Call<MovieDetail> getMovie(@Path("id") int id, @Query("api_key") String api_key);
}
