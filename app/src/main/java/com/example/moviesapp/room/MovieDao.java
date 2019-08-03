package com.example.moviesapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM MOVIE_TABLE WHERE id = :id")
    Movie getMovie(int id);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getFavouriteMovies();
}
