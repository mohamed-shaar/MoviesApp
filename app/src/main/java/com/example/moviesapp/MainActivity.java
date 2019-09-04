package com.example.moviesapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.adapter.PosterAdapter;
import com.example.moviesapp.api.Client;
import com.example.moviesapp.api.JsonPlaceHolderApi;
import com.example.moviesapp.model.MovieResult;
import com.example.moviesapp.model.RequestInformation;
import com.example.moviesapp.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PosterAdapter.OnItemClickListener{

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_RELEASE_DATE = "release_date";
    public static final String EXTRA_POSTER_PATH = "poster_path";
    public static final String EXTRA_VOTE_AVERAGE = "vote_average";
    public static final String EXTRA_PLOT_SYNOPSIS = "plot_synopsis";
    public static final String EXTRA_ID = "id";
    public static final String BASE_URL = "http://image.tmdb.org/t/p/original";

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<String> posterUrls;
    private List<MovieResult> movieResultList;

    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;
    private ProgressBar progressBar;

    //API key
    private String apiKey = "f34c452797e2d497fae6179c165c4f4a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonPlaceHolderApi = Client.getRetrofit().create(JsonPlaceHolderApi.class);
        posterUrls = new ArrayList<>();
        movieResultList = new ArrayList<MovieResult>();

        recyclerView = findViewById(R.id.rv_posters);
        progressBar = findViewById(R.id.progressBar);
        showProgressBar();
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dptopx(2), true));
        loadPopularMovies();
        posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
        recyclerView.setAdapter(posterAdapter);
        posterAdapter.setOnItemClickListener(MainActivity.this);
    }

    private void loadTopRatedMovies() {
        //Map<String, String> parameters = new HashMap<>();
        //API key
        //parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");
        posterUrls.clear();
        setTitle(R.string.top_rated_movies);
        Call<RequestInformation> call = jsonPlaceHolderApi.getTopRatedMovies(apiKey);

        call.enqueue(new Callback<RequestInformation>() {
            @Override
            public void onResponse(Call<RequestInformation> call, Response<RequestInformation> response) {
                if (!response.isSuccessful()) {
                    int code = response.code();
                    Log.d("Code: ", String.valueOf(code));
                    return;
                }
                else {
                    RequestInformation requestInformation = response.body();
                    movieResultList = requestInformation.getMovieResults();
                    for (MovieResult movieResult : requestInformation.getMovieResults()){
                        Log.d("Poster Path ", BASE_URL + movieResult.getPosterPath());
                        posterUrls.add(BASE_URL + movieResult.getPosterPath());
                    }
                    posterAdapter.notifyDataSetChanged();
                    showRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });
    }

    private void loadPopularMovies(){
        //Map<String, String> parameters = new HashMap<>();
        //API key
        //parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");
        setTitle(R.string.popular_movies);
        posterUrls.clear();
        Call<RequestInformation> call = jsonPlaceHolderApi.getPopularMovies(apiKey);

        call.enqueue(new Callback<RequestInformation>() {
            @Override
            public void onResponse(Call<RequestInformation> call, Response<RequestInformation> response) {
                if (!response.isSuccessful()) {
                    int code = response.code();
                    Log.d("Code: ", String.valueOf(code));
                    return;
                }
                else {
                    RequestInformation requestInformation = response.body();
                    movieResultList = requestInformation.getMovieResults();
                    for (MovieResult movieResult : requestInformation.getMovieResults()){
                        Log.d("Poster Path ", BASE_URL + movieResult.getPosterPath());
                        posterUrls.add(BASE_URL + movieResult.getPosterPath());
                    }
                    posterAdapter.notifyDataSetChanged();
                    showRecyclerView();
                    Log.d("adapter count ",""+ posterAdapter.getItemCount());

                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_top_rated_movies:
                loadTopRatedMovies();
                return  true;
            case R.id.menu_popular_movies:
                loadPopularMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(EXTRA_TITLE, movieResultList.get(position).getTitle());
        detailIntent.putExtra(EXTRA_RELEASE_DATE, movieResultList.get(position).getReleaseDate());
        detailIntent.putExtra(EXTRA_POSTER_PATH, movieResultList.get(position).getPosterPath());
        detailIntent.putExtra(EXTRA_VOTE_AVERAGE, movieResultList.get(position).getVoteAverage());
        detailIntent.putExtra(EXTRA_PLOT_SYNOPSIS, movieResultList.get(position).getOverview());
        detailIntent.putExtra(EXTRA_ID, movieResultList.get(position).getId());
        startActivity(detailIntent);
    }

    private int dptopx(int dp){
        Resources resource = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics()));
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }
    private void showRecyclerView(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
