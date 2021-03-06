package com.example.avanscinema.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.API.ResponseListener;
import com.example.avanscinema.API.UserListsResponseListener;
import com.example.avanscinema.Adapters.RecyclerAdapterUserListPage;
import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserListsResponseListener {
    private RecyclerView recyclerView;

    // Drawer menu class attributes
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private ApiConnection api = new ApiConnection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Get recyclerview component and set a linear layout
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listFormIntent = new Intent(UserListActivity.this, UserListCreateActivity.class);
                startActivity(listFormIntent);
            }
        });


        api.getUserMovieLists(this);

        setupDrawerMenu();
    }

    private void setRecyclerViewAdapter(List<UserMovieList> userMovieLists) {
        RecyclerAdapterUserListPage userMovieListAdapter = new RecyclerAdapterUserListPage(this, userMovieLists);
        userMovieListAdapter.setItemClickListener(new RecyclerAdapterUserListPage.ItemClickListener() {
            // Override the onItemClick method for the ClickListener interface
            @Override
            public void onItemClick(int id, View v) {
                Intent intent = new Intent(UserListActivity.this, UserListDetailActivity.class);
                intent.putExtra("LIST_ID", id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(userMovieListAdapter);
    }

    private void setupDrawerMenu() {
        Toolbar toolbar = findViewById(R.id.user_list_toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.user_list_drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.Open, R.string.Close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.user_list_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Check whether the drawer menu has been toggled
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return false;
    }

    // Method defining a on-click listener for the drawer menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Check witch item in the drawer has been clicked
        switch(item.getItemId()) {
            case R.id.Movie_list:
            case R.id.Favourites:
                Intent mainActivityIntent = new Intent(UserListActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                UserListActivity.super.finish();
                break;
            case R.id.settings:
                Intent settingsPageIntent = new Intent(UserListActivity.this, SettingsPage.class);
                startActivity(settingsPageIntent);
                UserListActivity.super.finish();
                break;
            case R.id.usermovielist:
                Intent userListsIntent = new Intent(UserListActivity.this, UserListActivity.class);
                startActivity(userListsIntent);
                UserListActivity.super.finish();
                break;
        }

        // Close drawer after clicking menu item
        drawer.close();
        return true;
    }

    @Override
    public void onUserMovieListResponse(List<UserMovieList> userMovieLists) {
        setRecyclerViewAdapter(userMovieLists);
    }
}