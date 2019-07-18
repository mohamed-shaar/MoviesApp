package com.example.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

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
                    RequestInformation requestInformation = response.body();
                    int page = requestInformation.getPage();
                    Log.d("pages top rated: ", String.valueOf(page));
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
                    RequestInformation requestInformation = response.body();
                    int page = requestInformation.getPage();
                    Log.d("pages popular: ", String.valueOf(page));
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
}
