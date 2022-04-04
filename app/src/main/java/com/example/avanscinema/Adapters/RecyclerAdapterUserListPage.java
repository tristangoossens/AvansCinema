package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Classes.UserMovieList;
import com.example.avanscinema.R;

import java.util.List;

public class RecyclerAdapterUserListPage extends RecyclerView.Adapter<RecyclerAdapterUserListPage.ViewHolder> {
    private List<UserMovieList> movieLists;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerAdapterUserListPage(Context context, List<UserMovieList> movieLists){
        this.inflater = LayoutInflater.from(context);
        this.movieLists = movieLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserMovieList list = this.movieLists.get(position);

        holder.titleTextView.setText(list.getName());
        holder.descriptionTextView.setText(list.getDescription());
        holder.countTextView.setText(String.format("(%d item(s))", list.getItemCount()));
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView countTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.titleTextView = itemView.findViewById(R.id.user_list_title);
            this.descriptionTextView = itemView.findViewById(R.id.user_list_description);
            this.countTextView = itemView.findViewById(R.id.user_list_count);
        }
    }
}
