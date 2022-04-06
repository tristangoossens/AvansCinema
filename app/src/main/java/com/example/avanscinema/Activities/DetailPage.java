package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.UserListsResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapterActorDetailPage;
import com.example.avanscinema.Adapters.RecyclerAdapterCompanyDetailPage;
import com.example.avanscinema.Adapters.RecyclerAdapterReviewDetailPage;
import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.Classes.Genre;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.ProductionCompany;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailPage extends AppCompatActivity implements ApiConnection.detailResponse, UserListsResponseListener {

    private static final String TAG = DetailPage.class.getSimpleName();
    private TextView Title, des, runtimeRelease, mGenre, mReview;
    private ImageButton share, back;
    private ImageView poster;
    private RecyclerView recyclerView;
    private ArrayList<Cast> actorList;
    private ArrayList<Review> reviews;
    private ArrayList<ProductionCompany> pCompanies;
    private TrailerList trailers;
    private RatingBar starRating;
    private Button sendButton;
    private Movie movie;
    private ListWithId selectedList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Title = findViewById(R.id.title_movie);
        poster = findViewById(R.id.detail_movie);
        des = findViewById(R.id.description_detailpage);
        runtimeRelease = findViewById(R.id.runtime_releaseDate_detailview);
        mGenre = findViewById(R.id.genre_detailview);
        mReview = findViewById(R.id.review_detailpage);
        share = findViewById(R.id.share_detail);
        back = findViewById(R.id.back_button);
        starRating = findViewById(R.id.ratingBar_detailpage);
        sendButton = findViewById(R.id.ratting_button_detailpage);
        Button saveToListButton = findViewById(R.id.detail_list_save);


        getIntentData();
        setupActorRecyclerView();
        setupCompanyRecyclerView();
        Collections.reverse(this.reviews);
        setupReviewRecyclerView();

        // Get list names and save to list
        ApiConnection api = new ApiConnection();
        api.getUserMovieLists(this);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent video = new Intent(getApplicationContext(), TrailerPage.class);
                video.putExtra("trailer", trailers);
                startActivity(video);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, movie.getHomepage());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share this movie!");
                startActivity(shareIntent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DetailPage.super.finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = starRating.getRating() * 2;
                if (rating >= 1){
                    ApiConnection api = new ApiConnection();
                    api.rateMovie(DetailPage.this, movie.getId(), rating);
                } else {
                    Toast.makeText(DetailPage.this, getResources().getString(R.string.ratingtoast), Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.addMovieToList(DetailPage.this, selectedList.id, movie.getId());
            }
        });
    }

    private void getIntentData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            retrieveInfo(intent);
            Title.setText(movie.getTitle());
            des.setText(movie.getOverview());
            runtimeRelease.setText(getResources().getString(R.string.lengte_en_datum_vanVerschijning, movie.getRuntime(), movie.getReleaseDate()));
            Picasso.get().load(movie.getLandscapeImage()).into(poster);
            StringBuilder sb = new StringBuilder();
           for (Genre g : movie.getGenres()) {
               sb.append(g.getName() + ", ");
           }
                mGenre.setText(getResources().getString(R.string.genre, sb));

            if (!reviews.isEmpty()){
                mReview.setVisibility(View.VISIBLE);
            }
        }
    }

    // Set up the spinner with all user made lists
    private void setupListSpinner(List<UserMovieList> userMovieLists){
        HashMap<String, Integer> userLists = new HashMap<>();
        List<ListWithId> itemList = new ArrayList<ListWithId>();
        for(UserMovieList list : userMovieLists){
            userLists.put(list.getName(), list.getId());
        }

        Spinner spin = (Spinner) findViewById(R.id.detail_list_spinner);

        for (Map.Entry<String, Integer> entry : userLists.entrySet()) {
            itemList.add(new ListWithId(entry.getKey(), entry.getValue()));
        }

        ArrayAdapter<ListWithId> spinnerAdapter = new ArrayAdapter<ListWithId>(this, android.R.layout.simple_spinner_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedList = (ListWithId) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing needs to happen
            }
        });
    }

    // Inner class to implement a key value pair for the list spinner
    private static class ListWithId {
        public String name;
        public Integer id;

        public ListWithId(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString(){
            return this.name;
        }
    }

    private void setupActorRecyclerView() {
        recyclerView = findViewById(R.id.actor_detail_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPage.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterActorDetailPage adapter = new RecyclerAdapterActorDetailPage(this.actorList);
        recyclerView.setAdapter(adapter);
    }

    private void setupCompanyRecyclerView() {
        recyclerView = findViewById(R.id.company_detail_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPage.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterCompanyDetailPage adapter = new RecyclerAdapterCompanyDetailPage(this.pCompanies);
        recyclerView.setAdapter(adapter);
    }
    private void setupReviewRecyclerView() {
        recyclerView = findViewById(R.id.review_detail_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPage.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterReviewDetailPage adapter = new RecyclerAdapterReviewDetailPage(this.reviews);
        recyclerView.setAdapter(adapter);
    }


    private void retrieveInfo(Intent intent) {
        CastList castlist = (CastList) intent.getSerializableExtra("cast");
        ReviewList list = (ReviewList) intent.getSerializableExtra("reviews");
        TrailerList trailers = (TrailerList) intent.getSerializableExtra("trailer");
        this.trailers = trailers;
        this.actorList = castlist.getCast();
        this.movie = (Movie) intent.getSerializableExtra("movie");
        this.reviews = list.getResults();
        this.pCompanies = movie.getProductionCompanies();
    }

    @Override
    public void onResponseRating(String message) {
        Toast.makeText(getApplicationContext(), "Rating: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListAddResponse(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserMovieListResponse(List<UserMovieList> userMovieLists) {
        setupListSpinner(userMovieLists);
    }
}
