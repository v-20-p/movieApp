package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.MoviePagingSource
import com.example.movieapp.data.dao.MovieDao
import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.Results
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) {
//    suspend fun getPopularMovies():UIState<SearchResponse>{
//        return try {
//            val response=movieApi.getUpcoming()
//            if (response.isSuccessful && response.body()!=null ){
//                UIState.Success(response.body())
//            }else{
//                UIState.Empty(message = response.message().toString())
//            }
//        }catch (e:Exception){
//            UIState.Error(e.message.toString())
//        }
//    }
     fun getPopularMovies(): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {

                MoviePagingSource(movieApi, movieDao = movieDao)
            }

        ).flow
    }
    fun getSearchMovies(query:String): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(movieApi,query,movieDao = movieDao )
            }

        ).flow
    }



}