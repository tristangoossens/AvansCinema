package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailPage extends AppCompatActivity {

    private static final String TAG = DetailPage.class.getSimpleName();
    private TextView Title, des;
    private ImageView poster;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Title = findViewById(R.id.title_movie);
        poster = findViewById(R.id.detail_movie);

        getIntentData();


    }

    private void getIntentData(){
        if (getIntent() !=null){
            Intent intent = getIntent();
           List<Cast> cast = retrieveCast(intent);
           List<Review> reviews = getReviews(intent);
            Movie movie = getMovie(intent);

            Title.setText(movie.getTitle());
            Picasso.get().load(movie.getLandscapeImage()).into(poster);
        }
    }


    private List<Cast> retrieveCast(Intent intent) {
        CastList cast = (CastList) intent.getSerializableExtra("cast");
        List<Cast> popes = cast.getCast();
        return popes;
    }
    private List<Review> getReviews(Intent intent) {
        ReviewList reviewlist = (ReviewList) intent.getSerializableExtra("reviews");
        List<Review> reviews = reviewlist.getResults();
        return reviews;
    }
    private Movie getMovie(Intent intent) {
        Movie movie = (Movie)intent.getSerializableExtra("movie");
        return movie;
    }
}
