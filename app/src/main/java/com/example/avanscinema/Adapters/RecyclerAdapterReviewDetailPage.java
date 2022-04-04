package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.avanscinema.Classes.Review;
import com.example.avanscinema.R;
import java.util.ArrayList;

public class RecyclerAdapterReviewDetailPage extends RecyclerView.Adapter<RecyclerAdapterReviewDetailPage.ReviewViewHolder>{
    private ArrayList<Review> mReviewlist;

    public RecyclerAdapterReviewDetailPage(ArrayList<Review> reviewlist){
        this.mReviewlist = reviewlist;
    }

    @NonNull
    @Override
    public RecyclerAdapterReviewDetailPage.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecyclerAdapterReviewDetailPage.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = mReviewlist.get(position);
        holder.mReviewUsername.setText(review.getAuthorName());
        holder.mReviewContent.setText(review.getContent());
        holder.mReviewCreateDate.setText(review.getCreatedAt());
        if (holder.mReviewContent.getText().length() > 200){
            holder.mShowMore.setText("Show More...");
            holder.mShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mShowMore.getText().toString().equalsIgnoreCase("Show More..."))
                    {
                        holder.mReviewContent.setMaxLines(Integer.MAX_VALUE);//your TextView
                        holder.mShowMore.setText("Show Less");
                    }
                    else
                    {
                        holder.mReviewContent.setMaxLines(4);//your TextView
                        holder.mShowMore.setText("Showmore...");
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mReviewlist.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        private final TextView mReviewUsername, mReviewContent, mReviewCreateDate, mShowMore;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mReviewContent = itemView.findViewById(R.id.content_review_cardview);
            mReviewCreateDate = itemView.findViewById(R.id.date_review_cardview);
            mReviewUsername = itemView.findViewById(R.id.author_review_cardview);
            mShowMore = itemView.findViewById(R.id.show_more_review_cardview);
        }


    }
}
