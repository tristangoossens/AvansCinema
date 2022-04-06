package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.UserListResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapterUserListDetailPage;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;

import java.util.List;

public class UserListDetailActivity extends AppCompatActivity implements UserListResponseListener {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_detail);

        // Get recyclerview component and set a linear layout
        recyclerView = (RecyclerView) findViewById(R.id.user_list_detail_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ApiConnection api = new ApiConnection();
        api.getUserMovieListDetails(UserListDetailActivity.this, getIntent().getIntExtra("LIST_ID", 1));


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
        TextView titleTextview = (TextView) findViewById(R.id.user_list_detail_title);
        TextView descriptionTextView = (TextView) findViewById(R.id.user_list_detail_description);

        titleTextview.setText(userMovieList.getName());
        descriptionTextView.setText(userMovieList.getDescription());

        setRecyclerViewAdapter(userMovieList.getMovies());
    }

    private void setRecyclerViewAdapter(List<Movie> movieList) {
        RecyclerAdapterUserListDetailPage userMovieListAdapter = new RecyclerAdapterUserListDetailPage(this, movieList);
        recyclerView.setAdapter(userMovieListAdapter);
    }


    @Override
    public void onUserListResponse(UserMovieList list) {
        setData(list);
    }
}