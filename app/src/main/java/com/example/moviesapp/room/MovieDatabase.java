package com.example.moviesapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class,
                    "movie_database")
                    .fallbackToDestructiveMigration() //when updating the database version this will cause all the information to be deleted
                    .build();
        }
        return instance;
    }
}
