package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Adapters.RecyclerAdapterDetailPage;
import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailPage extends AppCompatActivity {

    private static final String TAG = DetailPage.class.getSimpleName();
    private TextView Title, des, runtimeRelease;
    private ImageView poster;
    private RecyclerView recyclerView;
    private ArrayList<Cast> actorList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Title = findViewById(R.id.title_movie);
        poster = findViewById(R.id.detail_movie);
        des = findViewById(R.id.description_detailpage);
        runtimeRelease = findViewById(R.id.runtime_releaseDate_detailview);

        getIntentData();
        setupRecyclerView();


    }

    private void getIntentData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            actorList = retrieveCast(intent);
            ArrayList<Review> reviews = getReviews(intent);

            Movie movie = getMovie(intent);
            Title.setText(movie.getTitle());
            des.setText(movie.getOverview());
            runtimeRelease.setText(getString(R.string.Runtime)+" "+ movie.getRuntime() + getString(R.string.minutes) +   getString(R.string.releasedate) +" "+ movie.getReleaseDate());
            Picasso.get().load(movie.getLandscapeImage()).into(poster);
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.actor_detail_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPage.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterDetailPage adapter = new RecyclerAdapterDetailPage(this.actorList);
        recyclerView.setAdapter(adapter);
    }


    private ArrayList<Cast> retrieveCast(Intent intent) {
        CastList cast = (CastList) intent.getSerializableExtra("cast");
        ArrayList<Cast> cast1 = cast.getCast();
        return cast1;
    }

    private ArrayList<Review> getReviews(Intent intent) {
        ReviewList reviewlist = (ReviewList) intent.getSerializableExtra("reviews");
        ArrayList<Review> reviews = reviewlist.getResults();
        return reviews;
    }

    private Movie getMovie(Intent intent) {
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        return movie;
    }
}
