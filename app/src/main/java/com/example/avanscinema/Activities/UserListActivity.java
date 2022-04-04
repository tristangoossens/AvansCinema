package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;

import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<UserMovieList> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Get recyclerview component and set a linear layout
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRecyclerViewAdapter(List<UserMovieList> userList) {

    }
}