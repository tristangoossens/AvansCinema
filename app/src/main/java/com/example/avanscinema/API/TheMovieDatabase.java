package com.example.avanscinema.API;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabase {

    //Call voor ophalen van popular filmlijsten.
    @GET("movie/popular")
    Call<MovieList> listPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    //Call voor film Details van geselecteerde film.
    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String api_key);

    //Call voor search query (zoeken op film)
    @GET("search/movie")
    Call<MovieList> listFoundMovies(@Query("api_key") String api_key, @Query("query") String query);

    //Call voor Reviews van een movie
    @GET("movie/{id}/reviews")
    Call<ReviewList> listOfReviews(@Path("id") int id, @Query("api_key") String api_key);

    //Call voor cast van een movie
    @GET("movie/{id}/credits")
    Call<CastList> listOfCast(@Path("id") int id, @Query("api_key") String api_key);
}
