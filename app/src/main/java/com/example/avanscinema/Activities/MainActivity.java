package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.ResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapter;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    private RecyclerView recyclerView;
    private ApiConnection apiConnection;
    private Parcelable recyclerViewState;
    private ArrayList<Movie> movieList = new ArrayList<Movie>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBar;


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
        //Navigation Menu
        setupMenu();
    }

    private void setupMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBar.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.Movie_list:
                        recyclerViewState = null;
                        apiConnection.getPopularMoviesList(MainActivity.this, true);
                        break;
                    case R.id.Favourites:
                        //SHow list of Favourite movies
                        break;
                    case R.id.settings:
                        //Go to Settings
                        break;
                    case R.id.usermovielist:
                        Intent userListsIntent = new Intent(MainActivity.this, UserListActivity.class);
                        startActivity(userListsIntent);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBar.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void getMoviePopularList(ArrayList<Movie> result) {
        this.movieList.addAll(result);
    //Response call van API
        //Adapter wordt gemaakt
        //Clicked on Movie Item & nieuwe API call met deze Movie
        RecyclerAdapter adapter = new RecyclerAdapter(movieList, movie -> {
            //Clicked on Movie Item & nieuwe API call met deze Movie
            Log.d("movie chosen: ", movie.getTitle());
            apiConnection.getMovieDetails(MainActivity.this, movie.getId());

        }, position -> {
           if (position >= (movieList.size() - 4)) {
               //Load Next page
               recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
               Log.d("Touch: ", ""+ position);
               apiConnection.getPopularMoviesList(this, false);
           }
        });
    //Adapter wordt in recyclerView geplaatst
        recyclerView.setAdapter(adapter);
        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public void getDetails(Movie movie, ReviewList reviews, CastList cast, TrailerList trailer) {
        //Response van API Call van Movie Click
        //Detail page wordt pas geopend wanneer data aanwezig is.
        Intent detailPage = new Intent(getApplicationContext(), DetailPage.class);
        detailPage.putExtra("movie", movie);
        detailPage.putExtra("reviews", reviews);
        detailPage.putExtra("cast", cast);
        detailPage.putExtra("trailer", trailer);
        startActivity(detailPage);
    }

    @Override
    public void searchMovie(ArrayList<Movie> result) {
        //Weergeeft opgezochte films in een nieuwe Adapter
        RecyclerAdapter adapter = new RecyclerAdapter(result, movie -> {
                //Clicked on Movie Item & nieuwe API call met deze Movie (voor searched movies)
                apiConnection.getMovieDetails(MainActivity.this, movie.getId());
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
                    recyclerViewState = null;
                    movieList.clear();
                    apiConnection.getPopularMoviesList(MainActivity.this, true);
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
        recyclerViewState = null;
        movieList.clear();
        apiConnection.getPopularMoviesList(this, true);


    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}