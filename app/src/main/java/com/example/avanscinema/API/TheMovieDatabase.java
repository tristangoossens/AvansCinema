package com.example.avanscinema.API;

import com.example.avanscinema.API.RequestBody.AddToListRequestBody;
import com.example.avanscinema.Classes.Media;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Rating;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.GenreList;
import com.example.avanscinema.JsonParsers.ResponseMessage;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.MovieList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.JsonParsers.UserListsList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<MovieList> listFoundMovies(@Query("api_key") String api_key, @Query("query") String query, @Query("include_adult") boolean adult);

    //Call voor Reviews van een movie
    @GET("movie/{id}/reviews")
    Call<ReviewList> listOfReviews(@Path("id") int id, @Query("api_key") String api_key);

    //Call voor cast van een movie
    @GET("movie/{id}/credits")
    Call<CastList> listOfCast(@Path("id") int id, @Query("api_key") String api_key);

    //Call voor Trailer
    @GET("movie/{id}/videos")
    Call<TrailerList> listOfTrailers(@Path("id") int id, @Query("api_key") String api_key);

    // Call to list user created movie lists
    @GET("account/{account_id}/lists")
    Call<UserListsList> listUserMovieLists(@Path("account_id") int account_id, @Query("session_id") String session_id, @Query("api_key") String api_key);

    @GET("list/{list_id}")
    Call<UserMovieList> getUserListDetails(@Path("list_id") int list_id, @Query("api_key") String api_key);

    @GET("discover/movie")
    Call<MovieList> sortMoviesDate(@Query("api_key") String api_key, @Query("sort_by") String sorting);

    @GET("discover/movie")
    Call<MovieList> filterMoviesByGenre(@Query("api_key") String api_key, @Query("with_genres") int genre);

    @GET("discover/movie")
    Call<MovieList> filterOnDate(@Query("api_key") String api_key, @Query("primary_release_year") int year);

    @GET("genre/movie/list")
    Call<GenreList> getGenres(@Query("api_key") String api_key);

    @POST("movie/{id}/rating")
    Call<ResponseMessage> postRating(@Path("id") int id,@Query("api_key") String api_key,@Query("session_id") String session, @Body Rating rating);

    @GET("discover/movie")
    Call<MovieList> filteronRate(@Query("api_key") String api_key, @Query("vote_average.gte") int count, @Query("vote_average.lte") int count2);

    @POST("account/{id}/favorite")
    Call<ResponseMessage> markAsFavourite(@Path("id") int accId, @Query("api_key") String api_key, @Query("session_id") String session, @Body Media media);

    @GET("account/{id}/favorite/movies")
    Call<MovieList> getFavourite(@Path("id") int accId, @Query("session_id") String session, @Query("api_key") String api_key);

    @POST("list/{list_id}/add_item")
    Call<ResponseMessage> addMovieToList(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("session_id") String session_id, @Body AddToListRequestBody addToListRequestBody);
}

