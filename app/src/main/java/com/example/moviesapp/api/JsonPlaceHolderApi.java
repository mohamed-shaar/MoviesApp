package com.example.moviesapp.api;

import com.example.moviesapp.model.RequestInformation;
import com.example.moviesapp.model.Reviews;
import com.example.moviesapp.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("3/movie/popular")
    Call<RequestInformation> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/top_rated")
    Call<RequestInformation> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<Videos> getVideos(@Path("id")Integer movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<Reviews> getMovieReviews(@Path("id")Integer movieId, @Query("api_key") String apiKey);

}
