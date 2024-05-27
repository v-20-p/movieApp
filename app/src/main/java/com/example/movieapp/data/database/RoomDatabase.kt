package com.example.movieapp.data.database

import android.content.Context

import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.dao.MovieDao
import com.example.movieapp.model.MovieTable


@Database(entities = [MovieTable::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){
            abstract  fun  movieDao():MovieDao

            companion object{
                @Volatile
                private var  INSTANCE:MovieDatabase?=null
                fun getDatabase(context: Context):MovieDatabase{
                    return  INSTANCE?: synchronized(this){
                        val instance= Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java
                            ,"movie_database"
                        ).fallbackToDestructiveMigration().build()
                        INSTANCE=instance
                        instance
                    }
                }
            }

}