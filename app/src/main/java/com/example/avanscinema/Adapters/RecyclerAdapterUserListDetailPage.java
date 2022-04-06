package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapterUserListDetailPage extends RecyclerView.Adapter<RecyclerAdapterUserListDetailPage.ViewHolder> {
    private List<Movie> movieList;
    private LayoutInflater inflater;

    public RecyclerAdapterUserListDetailPage(Context context, List<Movie> movieLists){
        this.inflater = LayoutInflater.from(context);
        this.movieList = movieLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.user_list_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);
        holder.titleTextView.setText(movie.getOriginalTitle());
        Picasso.get()
                .load(String.format("https://image.tmdb.org/t/p/w500/%s", movie.getPosterPath()))
                .placeholder(R.drawable.movie_placeholder1)
                .fit()
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView posterImageView;
        private TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.posterImageView = itemView.findViewById(R.id.user_list_detail_movie_poster);
            this.titleTextView = itemView.findViewById(R.id.user_list_detail_movie_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
