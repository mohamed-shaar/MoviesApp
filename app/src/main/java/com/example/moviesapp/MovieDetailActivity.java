package com.example.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviesapp.room.Movie;
import com.example.moviesapp.room.MovieViewModel;
import com.squareup.picasso.Picasso;

import static com.example.moviesapp.MainActivity.BASE_URL;
import static com.example.moviesapp.MainActivity.EXTRA_ID;
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
    private Button btn_add_remove_favorites;

    private MovieViewModel movieViewModel;

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
        Integer id = detailIntent.getIntExtra(EXTRA_ID, 0);

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

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        Movie movie = new Movie(title, release_date, plot, vote_average, poster_path);
        movie.setId(id);
        movieViewModel.insert(movie);

        /**
         *  TODO
         *  1)check if movie has id in request (done)
         *  2)test database (done)
         *  3)check if button can be changed, if not then change text. ex. Add to favorites -> remove from favorites
         *  4)add recyclerView adapter for liked movies
         *  5)check if project needs savedInstance
         *  6)check youtube api from coding with mitch
         *  7)add trailers to intent data
         *  8)add background from request
         */
    }
}
