package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.UserListResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapterUserListDetailPage;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.JsonParsers.CastList;
import com.example.avanscinema.JsonParsers.ReviewList;
import com.example.avanscinema.JsonParsers.TrailerList;
import com.example.avanscinema.R;

import java.util.List;

public class UserListDetailActivity extends AppCompatActivity implements UserListResponseListener, ApiConnection.deleteResponse, ApiConnection.movieDetailsResponse, RecyclerAdapterUserListDetailPage.ItemClickListener , PopupMenu.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private ApiConnection api;
    private UserMovieList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_detail);

        // Get recyclerview component and set a linear layout
        recyclerView = (RecyclerView) findViewById(R.id.user_list_detail_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        api = new ApiConnection();
        api.getUserMovieListDetails(UserListDetailActivity.this, getIntent().getIntExtra("LIST_ID", 1));

        ImageButton optionsButton = (ImageButton) findViewById(R.id.user_list_detail_options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(UserListDetailActivity.this, view);
                popup.setOnMenuItemClickListener(UserListDetailActivity.this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.list_detail_menu, popup.getMenu());
                popup.show();
            }
        });
        ImageButton share = findViewById(R.id.share_private);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                for (Movie m : list.getMovies()) {
                    sb.append(m.getTitle() + ": " + m.getReleaseDate() + "\n");
                }

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share this movie!");
                startActivity(shareIntent);
            }
        });


        // Set onclick listener for back button
        ImageButton backButton = (ImageButton) findViewById(R.id.user_list_detail_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserListDetailActivity.super.finish();
            }
        });
    }

    private void setData(UserMovieList userMovieList) {
        list = userMovieList;

        TextView titleTextview = (TextView) findViewById(R.id.user_list_detail_title);
        TextView descriptionTextView = (TextView) findViewById(R.id.user_list_detail_description);

        titleTextview.setText(list.getName());
        descriptionTextView.setText(list.getDescription());

        setRecyclerViewAdapter(list.getMovies());
    }

    private void setRecyclerViewAdapter(List<Movie> movieList) {
        RecyclerAdapterUserListDetailPage userMovieListAdapter = new RecyclerAdapterUserListDetailPage(this, movieList);
        userMovieListAdapter.setItemClickListener(this);
        recyclerView.setAdapter(userMovieListAdapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                api.deleteMovieList(UserListDetailActivity.this, list.getId());
                break;
        }

        return true;
    }

    @Override
    public void onUserListResponse(UserMovieList list) {
        setData(list);
    }

    @Override
    public void onListDeleteResponse(String message) {
            Intent intent = new Intent(UserListDetailActivity.this, UserListActivity.class);
            startActivity(intent);
    }

    @Override
    public void onListItemDeleteResponse(String message) {
        Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserListDetailActivity.this, UserListDetailActivity.class);
        intent.putExtra("LIST_ID", list.getId());
        startActivity(intent);
    }

    @Override
    public void onItemClick(int id, View v) {
        api.getMovieDetails(UserListDetailActivity.this, id);
    }

    @Override
    public void onItemDeleteClick(int movie_id, View v) {
        api.deleteMovieFromList(UserListDetailActivity.this, list.getId(), movie_id);
    }

    @Override
    public void onCombinedDetailsResponse(Movie movie, ReviewList reviews, CastList cast, TrailerList trailer) {
        //Response van API Call van Movie Click
        //Detail page wordt pas geopend wanneer data aanwezig is.
        Intent detailPage = new Intent(getApplicationContext(), DetailPage.class);
        detailPage.putExtra("movie", movie);
        detailPage.putExtra("reviews", reviews);
        detailPage.putExtra("cast", cast);
        detailPage.putExtra("trailer", trailer);
        startActivity(detailPage);
        //Niet gesloten worden, want back button sluit detail page. Dan zou app sluiten
//        UserListDetailActivity.super.finish();
    }
}