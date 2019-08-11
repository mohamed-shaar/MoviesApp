package com.example.moviesapp.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAllMovies();
    }

    public void insert(Movie movie){ movieRepository.insert(movie);}

    public void delete(Movie movie){ movieRepository.delete(movie);}

    public Movie queryById(Movie movie) throws ExecutionException, InterruptedException { return movieRepository.queryById(movie);}

    public LiveData<Movie> liveDataQueryById(Movie movie){ return movieRepository.liveDataQueryById(movie);}

    public LiveData<List<Movie>> getAllMovies() {return allMovies;}
}
