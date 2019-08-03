package com.example.moviesapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public MovieRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
        allMovies = movieDao.getFavouriteMovies();
    }

    public void insert(Movie movie) {
        new InsertMovieAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteMovieAsyncTask(movieDao).execute(movie);
    }

    public Movie query(Movie movie) {
        QueryMovieAsyncTask asyncTask = new QueryMovieAsyncTask(movieDao);
        asyncTask.execute(movie);
        return asyncTask.getMovie();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private InsertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private DeleteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.delete(movies[0]);
            return null;
        }
    }

    private static class QueryMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;
        private Movie movie;

        private QueryMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movie = movieDao.getMovie(movies[0].getId());
            return null;
        }

        public Movie getMovie() {
            return movie;
        }
    }
}
