package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.ResponseListener;
import com.example.avanscinema.Adapters.recyclerAdapter;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    private RecyclerView recyclerView;
    private ApiConnection apiConnection;
    private Parcelable recyclerViewState;
    private ArrayList<Movie> movieList = new ArrayList<Movie>();

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
        this.movieList.addAll(result);
    //Response call van API
        //Adapter wordt gemaakt
        //Clicked on Movie Item & nieuwe API call met deze Movie
        recyclerAdapter adapter = new recyclerAdapter(movieList, movie -> {
            //Clicked on Movie Item & nieuwe API call met deze Movie

            apiConnection.getCast(MainActivity.this, movie.getId());

        }, position -> {
           if (position >= (movieList.size() - 4)) {
               //Load Next page
               recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
               Log.d("Touch: ", ""+ position);
               apiConnection.getPopularMoviesList(this);
           }
        });
    //Adapter wordt in recyclerView geplaatst
        recyclerView.setAdapter(adapter);
        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public void getDetails(Movie movie, ReviewList reviews, CastList cast) {
        //Response van API Call van Movie Click
        //Detail page wordt pas geopend wanneer data aanwezig is.
        Intent detailPage = new Intent(getApplicationContext(), DetailPage.class);
        detailPage.putExtra("movie", movie);
        detailPage.putExtra("reviews", reviews);
        detailPage.putExtra("cast", cast);
        startActivity(detailPage);
    }

    @Override
    public void searchMovie(ArrayList<Movie> result) {
        //Weergeeft opgezochte films in een nieuwe Adapter
        recyclerAdapter adapter = new recyclerAdapter(result, movie -> {
                //Clicked on Movie Item & nieuwe API call met deze Movie (voor searched movies)
                apiConnection.getCast(MainActivity.this, movie.getId());
            }, position -> {
            //Niet benodigt voor Search screen, had ook bestaande Method kunnen gebruiken maar dan problemen met Automatisch lijst extend.
                });
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchBar() {
        //Kleine zoek toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchView.getQuery().length() == 0) {
                    //Als zoekveld leeg is, reset hij de lijst naar Populaire films
                    apiConnection.getPopularMoviesList(MainActivity.this);
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Api call met gezochte text
                apiConnection.searchMovies(MainActivity.this, query);
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Onnodig omdat we  niet filteren op naam
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