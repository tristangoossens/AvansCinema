package com.example.avanscinema.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.avanscinema.Classes.Trailer;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class TrailerPage extends YouTubeBaseActivity {

    public ArrayList<Trailer> allTrailers;
    public String youtube_api_key = "AIzaSyAiQo8JfHveZkg49sQRWqf3lCuW6hY1PyU";
    public Trailer trailerClip;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_trailer);

        YouTubePlayerView trailer = findViewById(R.id.trailer);
        Intent intent = getIntent();
        TrailerList trailerList = (TrailerList) intent.getSerializableExtra("trailer");
        allTrailers = trailerList.getResults();
        for (Trailer t : allTrailers) {
            if (t.getName().toLowerCase().contains("trailer")) {
                trailerClip = t;
                break;
            }
        }
        if (trailerClip == null) {
            trailerClip = allTrailers.get(0);
        }

        trailer.initialize(youtube_api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(trailerClip.getKey());
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Video player Failed, make sure internet connection is established", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
