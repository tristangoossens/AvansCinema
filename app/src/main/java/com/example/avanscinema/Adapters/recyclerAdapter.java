package com.example.avanscinema.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
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
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        return new MovieHolder(view);
    }

    @Override
    //Bind de movies aan de recyclerView
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        String title = MovieList.get(position).getTitle();
        Integer ID = MovieList.get(position).getId();
        holder.Moviext.setText(title);
        Picasso.get().load(MovieList.get(position).getImage()).resize(150, 200).centerCrop().into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            mMovieClickListener.onMovieClick(MovieList.get(position));
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
        private final TextView Moviext;
        private TextView nickTxt;
        private final ImageView image;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            Moviext = itemView.findViewById(R.id.Title);
            image = itemView.findViewById(R.id.movie_poster);
        }
    }
}