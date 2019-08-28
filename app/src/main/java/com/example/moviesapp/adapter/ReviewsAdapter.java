package com.example.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{

    private Context context;
    private ArrayList<String> authorsList;
    private ArrayList<String> contentList;

    public ReviewsAdapter(Context context, ArrayList<String> authorsList, ArrayList<String> contentList) {
        this.context = context;
        this.authorsList = authorsList;
        this.contentList = contentList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        String author = authorsList.get(position);
        String content = contentList.get(position);
        holder.tv_review_author.setText(author);
        holder.tv_review_content.setText(content);
    }

    @Override
    public int getItemCount() {
        return authorsList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_review_author;
        public TextView tv_review_content;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_review_author = itemView.findViewById(R.id.tv_review_author);
            tv_review_content = itemView.findViewById(R.id.tv_review_content);
        }
    }
}
