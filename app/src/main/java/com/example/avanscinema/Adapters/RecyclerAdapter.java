package com.example.avanscinema.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MovieHolder> {

    private final String TAG = getClass().getSimpleName();
    private final ArrayList<Movie> MovieList;
    private final ItemClickListener mMovieClickListener;
    private final OnTouchListener mTouchListener;

    public RecyclerAdapter(ArrayList<Movie> MovieList, ItemClickListener itemMovieClickListener, OnTouchListener touchListener) {
        this.mMovieClickListener = itemMovieClickListener;
        this.MovieList = MovieList;
        this.mTouchListener = touchListener;
    }

    @NonNull
    @Override
    //Layout van de adapter
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new MovieHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    //Bind de movies aan de recyclerView
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = MovieList.get(position);
        String title = movie.getTitle();
        String overView = movie.getOverview();
        StringBuilder sb = new StringBuilder();

        if (overView.length() == 0) {
            overView = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        }
        Integer ID = movie.getId();
        holder.Title.setText(title);
        Picasso.get().load(movie.getImage()).resize(150, 200).centerCrop().into(holder.image);
        holder.mDescription.setText(overView);
        holder.Date.setText(MovieList.get(position).getReleaseDate());
        //Click listener voor het openen van Detail scherm
        holder.itemView.setOnClickListener(view -> {
            mMovieClickListener.onMovieClick(MovieList.get(position));
        });
        //Touch Listener voor het laden van volgende pagina
        holder.itemView.setOnTouchListener((view, motionEvent) -> {
            mTouchListener.returnItemPosition(position);
            return false;
        });
        ApiConnection api = new ApiConnection();
        holder.favour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favour.setVisibility(View.INVISIBLE);
                holder.unfavour.setVisibility(View.VISIBLE);

                api.markAsFavourite(movie.getId(), false);

            }
        });

        holder.unfavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.unfavour.setVisibility(View.GONE);
                holder.favour.setVisibility(View.VISIBLE);

                api.markAsFavourite(movie.getId(), true);
            }
        });

        holder.mRatingBar.setRating(movie.getStarRating());

        //Deze listener voorkomt dat de recyclerview scrolled. Zo kan de nested scrollview scrollen.
        holder.mScrollViewDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().getParent().getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }


    //Kleine interface voor ItemClickListener
    public interface ItemClickListener {
        void onMovieClick(Movie movie);
    }

    //Interface voor Position wanneer scherm wordt aangeraakt
    public interface OnTouchListener {
        void returnItemPosition(int position);
    }

    //xml wordt hier gekoppeld aan de velden
    public class MovieHolder extends RecyclerView.ViewHolder {
        private final TextView Title, mDescription, Date;
        private final ImageView image;
        private final ScrollView mScrollViewDescription;
        private final RatingBar mRatingBar;
        private final ImageButton unfavour, favour;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.releaseYear);
            Title = itemView.findViewById(R.id.Title);
            image = itemView.findViewById(R.id.movie_poster);
            mDescription = itemView.findViewById(R.id.description);
            mScrollViewDescription = itemView.findViewById(R.id.description_scrollview);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            unfavour = itemView.findViewById(R.id.list_not_favour);
            favour = itemView.findViewById(R.id.list_favour);

        }
    }
}