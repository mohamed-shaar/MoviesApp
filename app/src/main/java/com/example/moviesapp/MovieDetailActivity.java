package com.example.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviesapp.room.Movie;
import com.example.moviesapp.room.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

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

    private String title;
    private String release_date;
    private String poster_path;
    private float vote_average;
    private String plot;
    private Integer id;
    private boolean state;

    private MovieViewModel movieViewModel;

    private Intent detailIntent;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        linkView();
        getData();
        setView();

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movie = new Movie(title, release_date, plot, vote_average, poster_path);
        movie.setId(id);
        //movieViewModel.insert(movie);

        try {
            Movie queriedMovie = movieViewModel.queryById(movie);
            if (queriedMovie != null){
                Log.d("Movie ", "regular query success.");
                Log.d("Movie ", "id: " + queriedMovie.getId());
                Log.d("Movie ", "title: " + queriedMovie.getTitle());
                btn_add_remove_favorites.setText(R.string.remove_from_favorites);
                state = true;
            }
            else{
                btn_add_remove_favorites.setText(R.string.add_to_favorites);
                state = false;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        btn_add_remove_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state){
                    movieViewModel.insert(movie);
                    state = true;
                    btn_add_remove_favorites.setText(R.string.remove_from_favorites);
                }
                else {
                    movieViewModel.delete(movie);
                    state = false;
                    btn_add_remove_favorites.setText(R.string.add_to_favorites);
                }
            }
        });

        /**
         *  TODO
         *  1)check if movie has id in request (done)
         *  2)test database (done)
         *  3)check if button can be changed, if not then change text. ex. Add to favorites -> remove from favorites(done)
         *  4)add recyclerView adapter for liked movies
         *  5)check if project needs savedInstance
         *  6)check youtube api from coding with mitch
         */
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void linkView(){
        iv_poster = findViewById(R.id.iv_detail_poster);
        tv_title = findViewById(R.id.tv_detail_title);
        tv_release_date = findViewById(R.id.tv_detail_release_date);
        tv_vote_average = findViewById(R.id.tv_detail_vote);
        tv_plot = findViewById(R.id.tv_detail_plot);
        btn_add_remove_favorites = findViewById(R.id.btn_add_remove_to_favorites);
    }

    private void getData(){
        detailIntent = getIntent();
        title = detailIntent.getStringExtra(EXTRA_TITLE);
        release_date = detailIntent.getStringExtra(EXTRA_RELEASE_DATE);
        poster_path = detailIntent.getStringExtra(EXTRA_POSTER_PATH);
        vote_average = detailIntent.getFloatExtra(EXTRA_VOTE_AVERAGE, 0);
        plot = detailIntent.getStringExtra(EXTRA_PLOT_SYNOPSIS);
        id = detailIntent.getIntExtra(EXTRA_ID, 0);
    }

    private void setView(){
        String posterUrl = BASE_URL + poster_path;
        Picasso.get().load(posterUrl).fit().centerCrop().into(iv_poster);
        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_vote_average.setText(String.valueOf(vote_average));
        tv_plot.setText(plot);
    }

}
