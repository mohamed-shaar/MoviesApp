package com.example.moviesapp;

import android.widget.ImageView;

import com.example.moviesapp.model.MovieResult;

public interface PosterClickListener {
    void onPosterItemClick(int i, MovieResult movieResult, ImageView shareImageView);
}
