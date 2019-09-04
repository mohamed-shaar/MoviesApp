package com.example.moviesapp.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private ArrayList<String> thumbnailUrls;
    private ArrayList<String> titles;
    private Context context;


    public TrailerAdapter(Context context, ArrayList<String> thumbnailUrls, ArrayList<String> titles) {
        this.context = context;
        this.thumbnailUrls = thumbnailUrls;
        this.titles = titles;
}

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        String thumbnailUrl = thumbnailUrls.get(position);
        String title = titles.get(position);
        holder.tv_trailer_name.setText(title);
        Picasso.get().load(thumbnailUrl).fit().centerInside().into(holder.iv_trailer_thumbnail);
    }

    @Override
    public int getItemCount() {
        return thumbnailUrls.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_trailer_name;
        public ImageView iv_trailer_thumbnail;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_trailer_thumbnail = itemView.findViewById(R.id.iv_trailer_thumbnail);
            tv_trailer_name = itemView.findViewById(R.id.tv_trailer_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        String trailerId = thumbnailUrls.get(position);
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerId));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + trailerId));
                        try{
                            appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(appIntent);
                        }catch (ActivityNotFoundException e){
                            e.printStackTrace();
                            webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(webIntent);
                        }
                    }
                }
            });
        }
    }
}
