package com.example.moviesapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PosterAdapter.OnItemClickListener{

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<String> posterUrls;// = new ArrayList<>();

    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        posterUrls = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_posters);
        recyclerView.setHasFixedSize(false);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dptopx(4), true));
        loadPopularMovies();
        posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
        //Log.d("Second Poster size:", String.valueOf(posterUrls.size()));
        recyclerView.setAdapter(posterAdapter);
        posterAdapter.setOnItemClickListener(MainActivity.this);

        //loadPopularMovies();
        //loadTopRatedMovies();


    }

    private void loadTopRatedMovies() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");
        posterUrls.clear();
        Call<RequestInformation> call = jsonPlaceHolderApi.getTopRatedMovies(parameters);

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
                    /*
                    int page = requestInformation.getPage();
                    Log.d("pages top rated: ", String.valueOf(page));*/
                    //buildPosterUrls(response.body());
                    //setLayout();
                    String baseUrl = "http://image.tmdb.org/t/p/w185";
                    for (Result result: requestInformation.getResults()){
                        Log.d("Poster Path ", baseUrl + result.getPosterPath());
                        posterUrls.add(baseUrl + result.getPosterPath());
                    }
                    posterAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });
        /*posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
        Log.d("Second Poster size:", String.valueOf(posterUrls.size()));
        recyclerView.setAdapter(posterAdapter);
        posterAdapter.setOnItemClickListener(MainActivity.this);*/

    }

    private void loadPopularMovies(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");
        posterUrls.clear();
        Call<RequestInformation> call = jsonPlaceHolderApi.getPopularMovies(parameters);

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
                    //int page = requestInformation.getResults().size();
                    //Log.d("results size: ", String.valueOf(page));
                    //buildPosterUrls(requestInformation);
                    //setLayout();
                    String baseUrl = "http://image.tmdb.org/t/p/w185";
                    for (Result result: requestInformation.getResults()){
                        Log.d("Poster Path ", baseUrl + result.getPosterPath());
                        posterUrls.add(baseUrl + result.getPosterPath());
                    }
                    //setLayout();
                    /*posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
                    Log.d("Second Poster size:", String.valueOf(posterUrls.size()));
                    recyclerView.setAdapter(posterAdapter);
                    posterAdapter.setOnItemClickListener(MainActivity.this);*/
                    posterAdapter.notifyDataSetChanged();
                    Log.d("adapter coubt ",""+ posterAdapter.getItemCount());

                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });
        /*posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
        Log.d("Second Poster size:", String.valueOf(posterUrls.size()));
        recyclerView.setAdapter(posterAdapter);
        posterAdapter.setOnItemClickListener(MainActivity.this);*/
    }

    private void buildPosterUrls(RequestInformation requestInformation){
        String baseUrl = " http://image.tmdb.org/t/p/w185";
        Log.d("First Results size ", String.valueOf(requestInformation.getResults().size()));
        for (Result result: requestInformation.getResults()){
            //Log.d("Poster Path ", baseUrl + result.getPosterPath());
            posterUrls.add(baseUrl + result.getPosterPath());
        }
        for (int i = 0; i < posterUrls.size(); i++) {
            Log.d("Poster " + i, posterUrls.get(i));
        }
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
                //setLayout();
                return  true;
            case R.id.menu_popular_movies:
                loadPopularMovies();
                //setLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);

        //startActivity(detailIntent);
    }

    private void setLayout(){
        posterAdapter = new PosterAdapter(MainActivity.this, posterUrls);
        //Log.d("Second Poster size:", String.valueOf(posterUrls.size()));
        recyclerView.setAdapter(posterAdapter);
        posterAdapter.setOnItemClickListener(MainActivity.this);
    }

    private int dptopx(int dp){
        Resources resource = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics()));
    }
}
