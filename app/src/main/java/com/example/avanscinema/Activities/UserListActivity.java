package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.ResponseListener;
import com.example.avanscinema.API.UserListResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapterUserListPage;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserListResponseListener {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Get recyclerview component and set a linear layout
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiConnection api = new ApiConnection();
        api.getUserMovieLists(this);
    }

    private void setRecyclerViewAdapter(List<UserMovieList> userMovieLists) {
        RecyclerAdapterUserListPage userMovieListAdapter = new RecyclerAdapterUserListPage(this, userMovieLists);
        recyclerView.setAdapter(userMovieListAdapter);
    }

    @Override
    public void onUserMovieListResponse(List<UserMovieList> userMovieLists) {
        setRecyclerViewAdapter(userMovieLists);
    }
}