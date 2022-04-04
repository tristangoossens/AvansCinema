package com.example.avanscinema.API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Trailer;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.MovieList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.JsonParsers.UserListsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    public String api_key = "839967f27e812330b73ed782f61f9286";

    private final String session_id = "7fecf28e278ddcb67c48cda88571d0d96135b614";
    private final int account_id = 9643096;
    int page = 0;
    boolean hasCast = false;
    boolean hasTrailer = false;
    boolean hasReviews = false;
    boolean hasMovie = false;

    CastList cast;
    Movie movie;
    ReviewList reviews;
    TrailerList trailers;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TheMovieDatabase service = retrofit.create(TheMovieDatabase.class);

    public void getPopularMoviesList(ResponseListener listener, boolean refresh) {
        if (refresh) {
            page = 0;
        }
        page = page + 1;
        Call<MovieList> call = service.listPopularMovies(api_key, page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                assert response.body() != null;
                listener.getMoviePopularList(response.body().getResults());
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.d("4", "" + t.getMessage());
            }
        });
    }

    public void searchMovies(ResponseListener listener, String query) {
        Call<MovieList> call = service.listFoundMovies(api_key, query);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                assert response.body() != null;
                listener.searchMovie(response.body().getResults());
            }

            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {

            }
        });
    }

    //Alle Calls die bij elkaar horen samengevoegd.
    public void getMovieDetails(ResponseListener listener, int id) {
        Call<Movie> callMovie = service.getMovie(id, api_key);
        Call<TrailerList> callTrailer = service.listOfTrailers(id, api_key);
        Call<ReviewList> callReviews = service.listOfReviews(id, api_key);
        Call<CastList> callCast = service.listOfCast(id, api_key);

        //Movie details of clicked
        callMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                assert response.body() != null;
                Log.d("Movie requested: ", "" + response.body().getTitle());
                hasMovie = true;
                movie = response.body();
                if (hasCast && hasTrailer && hasReviews && hasMovie) {
                    listener.getDetails(movie, reviews, cast, trailers);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

            }
        });
        //Reviews
        callReviews.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(@NonNull Call<ReviewList> call, @NonNull Response<ReviewList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                hasReviews = true;
                reviews = response.body();
                if (hasCast && hasTrailer && hasReviews && hasMovie) {
                    listener.getDetails(movie, reviews, cast, trailers);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewList> call, @NonNull Throwable t) {

            }
        });
        //Cast
        callCast.enqueue(new Callback<CastList>() {
            @Override
            public void onResponse(Call<CastList> call, Response<CastList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                hasCast = true;
                cast = response.body();
                if (hasCast && hasTrailer && hasReviews && hasMovie) {
                    listener.getDetails(movie, reviews, cast, trailers);
                }
            }

            @Override
            public void onFailure(Call<CastList> call, Throwable t) {

            }
        });
        //Trailer
        callTrailer.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }

                hasTrailer = true;
                trailers = response.body();
                if (hasCast && hasTrailer && hasReviews && hasMovie) {
                    listener.getDetails(movie, reviews, cast, trailers);
                }

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });
    }

    public void getUserMovieLists(UserListResponseListener responseListener){
        Call<UserListsList> callUserMovieLists = service.listUserMovieLists(this.account_id, this.session_id, this.api_key);

        callUserMovieLists.enqueue(new Callback<UserListsList>() {
            @Override
            public void onResponse(Call<UserListsList> call, Response<UserListsList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }

                Log.d("Test", "onResponse: +" + response.body());
                responseListener.onUserMovieListResponse(response.body().getUserMovieLists());
            }

            @Override
            public void onFailure(Call<UserListsList> call, Throwable t) {
                Log.d("Bruh", "Error -> " + t.getMessage());
            }
        });
    }

}

