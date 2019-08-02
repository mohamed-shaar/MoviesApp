package com.example.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.example.moviesapp.MainActivity.BASE_URL;
import static com.example.moviesapp.MainActivity.EXTRA_PLOT_SYNOPSIS;
import static com.example.moviesapp.MainActivity.EXTRA_POSTER_PATH;
import static com.example.moviesapp.MainActivity.EXTRA_RELEASE_DATE;
import static com.example.moviesapp.MainActivity.EXTRA_TITLE;
import static com.example.moviesapp.MainActivity.EXTRA_VOTE_AVERAGE;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView iv_poster;
    private TextView tv_title;
    private TextView tv_release_date;
    private TextView tv_vote_average;
    private TextView tv_plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent detailIntent = getIntent();
        String title = detailIntent.getStringExtra(EXTRA_TITLE);
        String release_date = detailIntent.getStringExtra(EXTRA_RELEASE_DATE);
        String poster_path = detailIntent.getStringExtra(EXTRA_POSTER_PATH);
        float vote_average = detailIntent.getFloatExtra(EXTRA_VOTE_AVERAGE, 0);
        String plot = detailIntent.getStringExtra(EXTRA_PLOT_SYNOPSIS);

        Log.d(" detail vote ", String.valueOf(vote_average));
        Log.d(" detail poster path", poster_path);

        iv_poster = findViewById(R.id.iv_detail_poster);
        tv_title = findViewById(R.id.tv_detail_title);
        tv_release_date = findViewById(R.id.tv_detail_release_date);
        tv_vote_average = findViewById(R.id.tv_detail_vote);
        tv_plot = findViewById(R.id.tv_detail_plot);

        String posterUrl = BASE_URL + poster_path;
        Log.d("detail poster url ", posterUrl);

        Picasso.get().load(posterUrl).fit().centerCrop().into(iv_poster);
        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_vote_average.setText(String.valueOf(vote_average));
        tv_plot.setText(plot);
    }
}
