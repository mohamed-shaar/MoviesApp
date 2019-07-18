package com.example.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private Context context;
    private ArrayList<String> posterList;

    public PosterAdapter(Context context, ArrayList<String> posterList) {
        this.context = context;
        this.posterList = posterList;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.poster_item, viewGroup, false);
        return new PosterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder posterViewHolder, int i) {
        String posterUrl = posterList.get(i);
        Picasso.get().load(posterUrl).fit().centerInside().into(posterViewHolder.iv_poster);
    }

    @Override
    public int getItemCount() {
        return posterList.size();
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_poster;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }
    }
}
