package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.ResponseListener;
import com.example.avanscinema.Adapters.recyclerAdapter;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    public RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ApiConnection apiConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Api Connectie + ophalen van populaire films
        setupApi();
        //Search bar Method
        setupSearchBar();
        //Recycler view Method
        setupRecyclerView();
    }




















    @Override
    public void getMoviePopularList(ArrayList<Movie> result) {
    //Response call van API
        //Adapter wordt gemaakt
        this.adapter = new recyclerAdapter(result, new recyclerAdapter.ItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                //Clicked on Movie Item & nieuwe API call met deze Movie
                apiConnection.getMovieDetails(MainActivity.this, movie.getId());
            }
        });
    //Adapter wordt in recyclerView geplaatst
        recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void getDetails(Movie movie) {
        //Response van API Call van Movie Click
        //Detail page wordt pas geopend wanneer data aanwezig is.
        Intent detailPage = new Intent(getApplicationContext(), DetailPage.class);
        detailPage.putExtra("movie", movie);
        startActivity(detailPage);
    }

    private void setupSearchBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apiConnection.searchMovies(MainActivity.this, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupApi() {
        //Api wordt gemaakt
        this.apiConnection = new ApiConnection();
        //Ascyhrone task voor ophalen van popular filmlijst
        apiConnection.getPopularMoviesList(this);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

}