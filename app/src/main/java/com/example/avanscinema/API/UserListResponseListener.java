package com.example.avanscinema.API;

import com.example.avanscinema.Classes.UserMovieList;

import java.util.List;

public interface UserListResponseListener {
    public void onUserMovieListResponse(List<UserMovieList> userMovieLists);
}
