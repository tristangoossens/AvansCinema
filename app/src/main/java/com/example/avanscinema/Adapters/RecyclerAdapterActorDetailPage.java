package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.avanscinema.Classes.Cast;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterActorDetailPage extends RecyclerView.Adapter<RecyclerAdapterActorDetailPage.ActorViewHolder> {
    private ArrayList<Cast> mCastArrayList;

    public RecyclerAdapterActorDetailPage(ArrayList<Cast> castArrayList){
        this.mCastArrayList = castArrayList;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.actor_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Cast cast = mCastArrayList.get(position);
        if (cast.getProfilePath() != null){
            Picasso.get().load(cast.getImage()).resize(120, 180).centerCrop().into(holder.actorImage);
        }
        holder.actorName.setText(cast.getName());
        holder.character.setText(cast.getCharacter());

    }

    @Override
    public int getItemCount() {
        return mCastArrayList.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder{
        private final ImageView actorImage;
        private final TextView actorName, character;


        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actor_image_cardview);
            actorName = itemView.findViewById(R.id.actor_name_cardview);
            character = itemView.findViewById(R.id.actor_character_cardview);
        }


    }
}
