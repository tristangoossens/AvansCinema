package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avanscinema.Classes.MovieDetail;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;


public class DetailPage extends AppCompatActivity {

    private TextView Title;
    private ImageView poster;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Title = findViewById(R.id.title_movie);
        poster = findViewById(R.id.detail_movie);

        Intent intent = getIntent();

        MovieDetail movie = (MovieDetail) intent.getSerializableExtra("movie");
        Title.setText(movie.getTitle());
        Picasso.get().load(movie.getImage()).resize(150, 200).centerCrop().into(poster);
    }
}
