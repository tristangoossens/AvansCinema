package com.example.avanscinema.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avanscinema.Classes.ProductionCompany;
import com.example.avanscinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterCompanyDetailPage extends RecyclerView.Adapter<RecyclerAdapterCompanyDetailPage.CompanyViewHolder>{
    private ArrayList<ProductionCompany> mCompanylist;

    public RecyclerAdapterCompanyDetailPage(ArrayList<ProductionCompany> companylist){
        this.mCompanylist = companylist;
    }

    @NonNull
    @Override
    public RecyclerAdapterCompanyDetailPage.CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.company_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecyclerAdapterCompanyDetailPage.CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        ProductionCompany pCompany = mCompanylist.get(position);
        if (pCompany.getLogoPath() != null){
            Picasso.get().load(pCompany.getImage()).into(holder.mCompanyImage);
        }
        holder.mCompanyName.setText(pCompany.getName());
    }



    @Override
    public int getItemCount() {
        return mCompanylist.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView mCompanyImage;
        private final TextView mCompanyName;


        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCompanyImage = itemView.findViewById(R.id.company_image_cardview);
            mCompanyName = itemView.findViewById(R.id.company_name_cardview);

        }


    }
}
