package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getPopularMovies():UIState<SearchResponse>{
        return try {
            val response=movieApi.getUpcoming()
            if (response.isSuccessful && response.body()!=null ){
                UIState.Success(response.body())
            }else{
                UIState.Empty(message = response.message().toString())
            }
        }catch (e:Exception){
            UIState.Error(e.message.toString())
        }
    }



}