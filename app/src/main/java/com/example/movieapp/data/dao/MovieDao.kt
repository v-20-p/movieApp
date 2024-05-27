package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.model.MovieTable


@Dao
interface MovieDao {
//    SELECT * FROM SampleFruits
//ORDER BY Id
//OFFSET 0 ROWS
//FETCH NEXT 7 ROWS ONLY


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieTable>)
    @Query( "select * from movie_table where page = :page")
    suspend fun getMovie(page:Int): List<MovieTable>
    @Transaction
    @Query("Delete from movie_table where page not in (:page)")
    suspend fun deleteMovieNotInPage(page:List<Int>)

    @Transaction
    @Query("select * from movie_table")
    suspend fun getCacheMovies():List<MovieTable>






}