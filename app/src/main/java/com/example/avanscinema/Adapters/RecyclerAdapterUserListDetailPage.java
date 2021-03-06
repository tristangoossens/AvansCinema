package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.Classes.Movie;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapterUserListDetailPage extends RecyclerView.Adapter<RecyclerAdapterUserListDetailPage.ViewHolder> {
    private List<Movie> movieList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

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

        if(movie.getOriginalTitle().length() > 15) {
            holder.titleTextView.setText(String.format("%s...", movie.getOriginalTitle().substring(0,15)));
        }else{
            holder.titleTextView.setText(movie.getOriginalTitle());
        }

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

    // On click interface that will be implemented by the activity class
    public interface ItemClickListener {
        void onItemClick(int id, View v);
        void onItemDeleteClick(int list_id, View v);
    }

    // Method to set the ItemClickListener
    public void setItemClickListener(RecyclerAdapterUserListDetailPage.ItemClickListener listener){
        this.clickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView posterImageView;
        private TextView titleTextView;
        private ImageButton deleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.posterImageView = itemView.findViewById(R.id.user_list_detail_movie_poster);
            this.titleTextView = itemView.findViewById(R.id.user_list_detail_movie_title);
            this.deleteImageButton = itemView.findViewById(R.id.user_list_detail_delete_button);

            this.deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemDeleteClick(movieList.get(getAdapterPosition()).getId(), view);
                }
            });

            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            clickListener.onItemClick(movieList.get(getAdapterPosition()).getId(), view);
        }
    }
}
