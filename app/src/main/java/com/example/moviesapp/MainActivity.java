package com.example.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private ArrayList<String> posterUrls;

    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_posters);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO stopped here

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        posterUrls = new ArrayList<>();

        //loadPopularMovies();
        //loadTopRatedMovies();


    }

    private void loadTopRatedMovies() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");

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
                    /*RequestInformation requestInformation = response.body();
                    int page = requestInformation.getPage();
                    Log.d("pages top rated: ", String.valueOf(page));*/
                    buildPosterUrls(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });
    }

    private void loadPopularMovies(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_key", "f34c452797e2d497fae6179c165c4f4a");

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
                    /*RequestInformation requestInformation = response.body();
                    int page = requestInformation.getPage();
                    Log.d("pages popular: ", String.valueOf(page));*/
                    buildPosterUrls(response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestInformation> call, Throwable t) {
                Log.d("Failure in request: ", t.getMessage());
            }
        });
    }

    private void buildPosterUrls(RequestInformation requestInformation){
        String baseUrl = " http://image.tmdb.org/t/p/w185";
        for (Result result: requestInformation.getResults()){
            posterUrls.add(baseUrl + result.getPosterPath());
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

    }
}
