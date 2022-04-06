package com.example.avanscinema.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.avanscinema.Classes.Genre;
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
    private String filter;
    private String order;
    private String sorter;
    private String genre;
    private Movie add;




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

        setupSpinners();

        shareButton();
    }

    private void shareButton() {
        ImageButton but = findViewById(R.id.share_button_main);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                for (Movie m : movieList) {
                sb.append(m.getTitle()+ ": " + m.getReleaseDate() + "\n");
                }

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share this movie!");
                startActivity(shareIntent);
            }
        });
    }

    private void setupSpinners() {
        Spinner spinnerF = findViewById(R.id.spinner_filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerF.setAdapter(adapter);
        spinnerF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //FILTER

                switch ((int) adapterView.getItemIdAtPosition(i)) {
                    case 0:
                        filter = "none";
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Filter on Date selected", Toast.LENGTH_SHORT);
                        filter = "date";
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Filter on Rate selected", Toast.LENGTH_SHORT);
                        filter = "rate";
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Filter on Genre selected", Toast.LENGTH_SHORT);
                        filter = "genre";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Spinner spinnerA = findViewById(R.id.spinner_asc_desc);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.asc_desc, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerA.setAdapter(adapter1);
        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //ASC - DESC
                switch ((int) adapterView.getItemIdAtPosition(i)) {
                    case 1:
                        order = "Desc";
                        break;
                    case 0:
                        order = "Asc";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Spinner spinnerS = findViewById(R.id.spinner_sorteren);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_list_sorteren, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerS.setAdapter(adapter2);
        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //SORT

                switch ((int) adapterView.getItemIdAtPosition(i)) {
                    case 0:
                        sorter = "none";
                        break;
                    case 1:
                        sorter = "date";
                        break;
                    case 2:
                        sorter = "rating";
                        break;
                    case 3:
                        sorter = "expected";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        EditText sort = findViewById(R.id.filterTxt);
        Button searchF = findViewById(R.id.filterButton);

        searchF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FILTER CALL
                switch (filter) {
                    case "none":
                        Toast.makeText(getApplicationContext(), "No filter selected!", Toast.LENGTH_SHORT).show();
                        break;
                    case "date":
                        if (sort.getText() == null) {
                            Toast.makeText(getApplicationContext(), "Please give the year of release", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int year = 0;
                        try {
                            year = Integer.parseInt(sort.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Please give the year (in numbers)", Toast.LENGTH_SHORT).show();
                        }
                        apiConnection.filterOnYear(MainActivity.this, year);
                        break;
                    case "genre":
                        if (sort.getText() == null) {
                            Toast.makeText(getApplicationContext(), "Enter a genre", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        genre = sort.getText().toString();
                        apiConnection.getGenres(MainActivity.this);
                        break;
                    case "rate":
                        if (sort.getText() == null) {
                            Toast.makeText(getApplicationContext(), "Please give a rating from 1-10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int rate = 0;
                        try {
                            rate = Integer.parseInt(sort.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Please give the rating (in numbers)", Toast.LENGTH_SHORT).show();
                            break;
                        }
                       apiConnection.filterOnRate(MainActivity.this, rate);
                        break;
                }
            }
        });
        Button sortButton = findViewById(R.id.SortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (sorter) {
                    case "date":
                        if (order.equalsIgnoreCase("asc")) {
                            reversesortOnRelease(movieList);
                        } else {
                            sortOnRelease(movieList);
                        }

                        RecyclerAdapter adapterRel = new RecyclerAdapter(movieList, movie -> {
                            //Clicked on Movie Item & nieuwe API call met deze Movie
                            Log.d("movie chosen: ", "" +movie.getId());
                            apiConnection.getMovieDetails(MainActivity.this, movie.getId());

                        }, position -> {

                        });
                        recyclerView.setAdapter(adapterRel);
                            break;

                    case "rating":
                        if (order.equalsIgnoreCase("asc")) {
                            reversesortOnRating(movieList);
                        } else {
                            sortOnRating(movieList);
                        }
                        RecyclerAdapter adapterRat = new RecyclerAdapter(movieList, movie -> {
                            //Clicked on Movie Item & nieuwe API call met deze Movie
                            Log.d("movie chosen: ", "" +movie.getId());
                            apiConnection.getMovieDetails(MainActivity.this, movie.getId());

                        }, position -> {

                        });
                        recyclerView.setAdapter(adapterRat);
                        break;
                    case "expected":

                        break;
                }
            }
        });
    }

    private void setupMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBar.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.Movie_list:
                        recyclerViewState = null;
                        apiConnection.getPopularMoviesList(MainActivity.this, true);
                        break;
                    case R.id.Favourites:
                        //Show list of Favourite movies
                        break;
                    case R.id.settings:
                        Intent settingsPageIntent = new Intent(MainActivity.this, SettingsPage.class);
                        startActivity(settingsPageIntent);
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
            Log.d("movie chosen: ", "" +movie.getId());
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
            this.movieList.clear();
            this.movieList.addAll(result);
        //Weergeeft opgezochte films in een nieuwe Adapter
        RecyclerAdapter adapter = new RecyclerAdapter(result, movie -> {
                //Clicked on Movie Item & nieuwe API call met deze Movie (voor searched movies)
            Log.d("movie chosen: ", "" +movie.getId());
                apiConnection.getMovieDetails(MainActivity.this, movie.getId());
            }, position -> {
            //Niet benodigt voor Search screen, had ook bestaande Method kunnen gebruiken maar dan problemen met Automatisch lijst extend.
                });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getGenres(ArrayList<Genre> genres) {
        boolean sendToast = true;
        for (Genre g : genres) {
            if (g.getName().toLowerCase().contains(this.genre)) {
                sendToast = false;
            apiConnection.filterMoviesByGenre(MainActivity.this, g.getId());
            break;
            }
        }
        if (sendToast) {
            Toast.makeText(getApplicationContext(), "This Genre could not be found!", Toast.LENGTH_SHORT).show();
        }
    }
    public void sortOnRelease(ArrayList<Movie> list)
    {
        list.sort((item1, item2) -> item1.getReleaseDate().compareTo(item2.getReleaseDate()));
    }

    public void reversesortOnRelease(ArrayList<Movie> list) {
        list.sort((item2, item1) -> item1.getReleaseDate().compareTo(item2.getReleaseDate()));
    }
    public void sortOnRating(ArrayList<Movie> list)
    {
        list.sort((item1, item2) -> item1.getVoteAverage().compareTo(item2.getVoteAverage()));
    }

    public void reversesortOnRating(ArrayList<Movie> list) {
        list.sort((item2, item1) -> item1.getVoteAverage().compareTo(item2.getVoteAverage()));
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