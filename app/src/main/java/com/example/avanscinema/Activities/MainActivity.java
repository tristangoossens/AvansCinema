package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.Api;
import com.example.avanscinema.API.responseListener;
import com.example.avanscinema.Adapters.recyclerAdapter;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.MovieDetail;
import com.example.avanscinema.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements responseListener {

    public RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.api = new Api();
        api.getPopularMoviesList(this);

        recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    public void getMoviePopularList(ArrayList<Movie> result) {

        this.adapter = new recyclerAdapter(result, new recyclerAdapter.ItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                //Clicked on Movie Item
                //Nieuwe API call
                api.getMovieDetails(MainActivity.this, movie.getId());
            }
        });

        recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void getDetails(MovieDetail movie) {
        //Response van API Call van Movie Click
        //Detail page wordt pas geopend wanneer data aanwezig is
        Intent detailPage = new Intent(getApplicationContext(), DetailPage.class);
        detailPage.putExtra("movie", movie);
        startActivity(detailPage);
    }
}