package com.example.avanscinema.API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.MovieList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    public String api_key = "839967f27e812330b73ed782f61f9286";
    int page = 0;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TheMovieDatabase service = retrofit.create(TheMovieDatabase.class);

    public void getPopularMoviesList(ResponseListener listener) {
        page = page + 1;
        Call<MovieList> call = service.listPopularMovies(api_key, page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, Response<MovieList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                listener.getMoviePopularList(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d("4", "" + t.getMessage());
            }
        });
    }

    public void getMovieDetails(ResponseListener listener, int id) {
        Call<Movie> call = service.getMovie(id, api_key);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                Log.d("Movie requested: ", "" + response.body().getTitle());
                listener.getDetails(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
    public void searchMovies(ResponseListener listener, String query) {
        Call<MovieList> call = service.listFoundMovies(api_key, query);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                listener.searchMovie(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

}

