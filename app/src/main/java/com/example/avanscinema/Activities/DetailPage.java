package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;


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
            Movie movie = (Movie)intent.getSerializableExtra("movie");
            Title.setText(movie.getTitle());
            Picasso.get().load(movie.getLandscapeImage()).into(poster);
        }
    }
}
