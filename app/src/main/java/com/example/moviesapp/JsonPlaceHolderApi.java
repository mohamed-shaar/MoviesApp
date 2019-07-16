package com.example.moviesapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {

    @GET("3/movie/popular")
    Call<RequestInformation> getPopularMovies(@QueryMap Map<String, String> parameters);

    @GET("3/movie/top_rated")
    Call<RequestInformation> getTopRatedMovies(@QueryMap Map<String, String> parameters);

}
