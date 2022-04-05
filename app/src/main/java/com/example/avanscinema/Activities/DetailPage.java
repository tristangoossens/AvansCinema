package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Adapters.RecyclerAdapterActorDetailPage;
import com.example.avanscinema.Adapters.RecyclerAdapterCompanyDetailPage;
import com.example.avanscinema.Adapters.RecyclerAdapterReviewDetailPage;
import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.Classes.Genre;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.ProductionCompany;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;


public class DetailPage extends AppCompatActivity {

    private static final String TAG = DetailPage.class.getSimpleName();
    private TextView Title, des, runtimeRelease, mGenre, mReview;
    private ImageButton share, back;
    private ImageView poster;
    private RecyclerView recyclerView;
    private ArrayList<Cast> actorList;
    private ArrayList<Review> reviews;
    private ArrayList<ProductionCompany> pCompanies;
    private TrailerList trailers;
    private LinearLayout mLinearLayoutReviews;
    private Movie movie;

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

        getIntentData();
        setupActorRecyclerView();
        setupCompanyRecyclerView();
        Collections.reverse(this.reviews);
        setupReviewRecyclerView();

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

}
