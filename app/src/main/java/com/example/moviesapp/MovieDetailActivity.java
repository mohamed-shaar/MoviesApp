package com.example.moviesapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.adapter.ReviewsAdapter;
import com.example.moviesapp.adapter.TrailerAdapter;
import com.example.moviesapp.api.Client;
import com.example.moviesapp.api.JsonPlaceHolderApi;
import com.example.moviesapp.model.ReviewResult;
import com.example.moviesapp.model.Reviews;
import com.example.moviesapp.model.VideoResult;
import com.example.moviesapp.model.Videos;
import com.example.moviesapp.room.Movie;
import com.example.moviesapp.room.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviesapp.MainActivity.BASE_URL;
import static com.example.moviesapp.MainActivity.EXTRA_ID;
import static com.example.moviesapp.MainActivity.EXTRA_PLOT_SYNOPSIS;
import static com.example.moviesapp.MainActivity.EXTRA_POSTER_PATH;
import static com.example.moviesapp.MainActivity.EXTRA_RELEASE_DATE;
import static com.example.moviesapp.MainActivity.EXTRA_TITLE;
import static com.example.moviesapp.MainActivity.EXTRA_TRANSITION_NAME;
import static com.example.moviesapp.MainActivity.EXTRA_VOTE_AVERAGE;

public class MovieDetailActivity extends AppCompatActivity{

    private static final String YOUTUBE_API_KEY = "AIzaSyAkZxHKYUU3XjI0-DuPjd-_gWLfzWVG9Lo";
    private static final String TAG = "MovieDetailActivity";
    //API key
    private String apiKey = "f34c452797e2d497fae6179c165c4f4a";

    //private YouTubePlayerView youTubePlayerView;
    //private YouTubePlayer.OnInitializedListener initializedListener;

    private ImageView iv_poster;
    private TextView tv_title;
    private TextView tv_release_date;
    private TextView tv_vote_average;
    private TextView tv_plot;
    private Button btn_add_remove_favorites;
    private RecyclerView rv_trailers;
    private TrailerAdapter trailerAdapter;
    private RecyclerView rv_reviews;
    private ReviewsAdapter reviewsAdapter;

    private String title;
    private String release_date;
    private String poster_path;
    private float vote_average;
    private String plot;
    private Integer id;
    private boolean state;

    private ArrayList<String> keys;
    private ArrayList<String> titles;
    private ArrayList<String> authorsList;
    private ArrayList<String> contentList;

    private MovieViewModel movieViewModel;

    private Intent detailIntent;

    private Movie movie;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        linkView();
        getData();
        setView();

        rv_trailers = findViewById(R.id.rv_trailers);
        rv_trailers.setHasFixedSize(false);
        rv_trailers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        trailerAdapter = new TrailerAdapter(getApplicationContext(), keys, titles);
        rv_trailers.setAdapter(trailerAdapter);

        rv_reviews = findViewById(R.id.rv_reviews);
        rv_reviews.setHasFixedSize(false);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this));
        reviewsAdapter = new ReviewsAdapter(getApplicationContext(), authorsList, contentList);
        rv_reviews.setAdapter(reviewsAdapter);




        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movie = new Movie(title, release_date, plot, vote_average, poster_path);
        movie.setId(id);

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

        jsonPlaceHolderApi = Client.getRetrofit().create(JsonPlaceHolderApi.class);


        Call<Videos> trailersCall = jsonPlaceHolderApi.getVideos(id, apiKey);
        Call<Reviews> reviewsCall = jsonPlaceHolderApi.getMovieReviews(id, apiKey);

        trailersCall.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                if (!response.isSuccessful()) {
                    int code = response.code();
                    Log.d("Video Code: ", String.valueOf(code));
                    return;
                }
                else {
                    Videos videos = response.body();
                    final List<VideoResult> videoResultList = videos.getVideoResults();
                    String youtubeThumbnailUrl = "https://img.youtube.com/vi/";
                    for (VideoResult current: videoResultList){
                        Log.d("Video key:", current.getKey());
                        keys.add(youtubeThumbnailUrl + current.getKey() + "/hqdefault.jpg");
                        titles.add(current.getName());
                    }
                    trailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });

        reviewsCall.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (!response.isSuccessful()) {
                    int code = response.code();
                    Log.d("Video Code: ", String.valueOf(code));
                    return;
                }
                else {
                    Reviews reviews = response.body();
                    List<ReviewResult> reviewResultList = reviews.getResults();
                    for (ReviewResult current: reviewResultList){
                        Log.d("Result: ", current.getAuthor());
                        authorsList.add(current.getAuthor());
                        contentList.add(current.getContent());
                    }
                    reviewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });

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
        bundle = detailIntent.getExtras();
    }

    private void setView(){
        String posterUrl = BASE_URL + poster_path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = bundle.getString(EXTRA_TRANSITION_NAME);
            iv_poster.setTransitionName(imageTransitionName);
        }
        Picasso.get().load(posterUrl).fit().centerCrop().into(iv_poster, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                supportStartPostponedEnterTransition();
            }
        });
        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_vote_average.setText(String.valueOf(vote_average));
        tv_plot.setText(plot);
        keys = new ArrayList<>();
        titles = new ArrayList<>();
        authorsList = new ArrayList<>();
        contentList = new ArrayList<>();
        setTitle(title);
    }
}
