package com.example.avanscinema.Adapters;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MovieHolder> implements Filterable {

    private final String TAG = getClass().getSimpleName();
    private final ArrayList<Movie> MovieList;
    private final ItemClickListener mMovieClickListener;
    private final ArrayList<Movie> MovieListFull;
    private final Filter MovieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Movie> filteredList = new ArrayList<Movie>();
//Als er niks ingevuld is wordt de hele lijst weergeven/ anders wordt er een constraint opgesteld.
            if (constraint == null || constraint.length() == 0) {
                Log.d(TAG, "Movieing Moviesize: " + MovieListFull.size());
                filteredList.addAll(MovieListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
//Constraint wordt gefilterd over de hele lijst, kijken welke Movie namen overeenkomen
                for (Movie Movie : MovieListFull) {
                    if (Movie.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(Movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
//Waardes worden in de lijst toegepast, waardoor je een gefilterde lijst krijgt

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            MovieList.clear();
            MovieList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

    };

    public recyclerAdapter(ArrayList<Movie> MovieList, ItemClickListener itemMovieClickListener) {
        this.mMovieClickListener = itemMovieClickListener;
        this.MovieList = MovieList;
        this.MovieListFull = new ArrayList<>(MovieList);
    }

    @NonNull
    @Override
    //Layout van de adapter
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);


        return new MovieHolder(view);
    }

    @Override
    //Bind de movies aan de recyclerView
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = MovieList.get(position);
        String title = movie.getTitle();
        String overView = movie.getOverview();
        Integer ID = movie.getId();
        holder.Title.setText(title);
        Picasso.get().load(movie.getImage()).resize(150, 200).centerCrop().into(holder.image);
        holder.mDescription.setText(overView);
        holder.itemView.setOnClickListener(view -> {
            mMovieClickListener.onMovieClick(MovieList.get(position));
        });
        holder.mRatingBar.setRating(movie.getStarRating());

        //Deze listener voorkomt dat de recyclerview scrolled. Zo kan de nested scrollview scrollen. Geen idee waarom hij geel gemarkeerd is.
        holder.mScrollViewDescription.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
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

    public Filter getFilter() {
        return MovieFilter;
    }

    //Kleine interface voor ItemClickListener
    public interface ItemClickListener {
        void onMovieClick(Movie movie);
    }

    //xml wordt hier gekoppeld aan de velden
    public class MovieHolder extends RecyclerView.ViewHolder {
        private final TextView Title, mDescription;
        private final ImageView image;
        private final ScrollView mScrollViewDescription;
        private final RatingBar mRatingBar;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.Title);
            image = itemView.findViewById(R.id.movie_poster);
            mDescription = itemView.findViewById(R.id.description);
            mScrollViewDescription = itemView.findViewById(R.id.description_scrollview);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}