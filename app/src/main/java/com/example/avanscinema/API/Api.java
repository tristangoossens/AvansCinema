package com.example.avanscinema.API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.avanscinema.Classes.MovieDetail;
import com.example.avanscinema.Classes.MovieList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public String api_key = "839967f27e812330b73ed782f61f9286";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TheMovieDatabase service = retrofit.create(TheMovieDatabase.class);

    public void getPopularMoviesList(responseListener listener) {
        Call<MovieList> call = service.listMovies(api_key);
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

    public void getMovieDetails(responseListener listener, int id) {
        Call<MovieDetail> call = service.getMovie(id, api_key);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (!(response.code() == 200)) {
                    Log.d("Bruh", "Error -> " + response.code());
                    return;
                }
                Log.d("Movie requested: ", "" + response.body().getTitle());
                listener.getDetails(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });

    }
}

