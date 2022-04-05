package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.UserListResponseListener;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;

public class UserListDetailActivity extends AppCompatActivity implements UserListResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_detail);

        ApiConnection api = new ApiConnection();
        api.getUserMovieListDetails(UserListDetailActivity.this, getIntent().getIntExtra("LIST_ID", 1));
    }

    private void setData(UserMovieList userMovieList) {

    }

    @Override
    public void onUserListResponse(UserMovieList list) {
        setData(list);
    }
}