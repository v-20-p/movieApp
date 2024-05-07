package com.example.movieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.Results
import okio.IOException

class MoviePagingSource (
    private val movieApi: MovieApi,
    private val query: String? = null
):PagingSource<Int,Results>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            if (query == null) {
                val correntPage = params.key ?: 1
                val movie = movieApi.getUpcoming(
                    apiKey = BuildConfig.TMOB_API_KEY,
                    page = correntPage
                )
                LoadResult.Page(
                    data = movie.body()?.results.orEmpty(),
                    prevKey = if (correntPage == 1) null else correntPage - 1,
                    nextKey = if (movie.body()?.results?.isEmpty() == true) null else movie.body()?.page!! + 1
                )
            }else{
                val correntPage = params.key ?: 1
                val movie = movieApi.getSearch(
                    apiKey = BuildConfig.TMOB_API_KEY,
                    page = correntPage,
                    query = query
                )
                LoadResult.Page(
                    data = movie.body()?.results.orEmpty(),
                    prevKey = if (correntPage == 1) null else correntPage - 1,
                    nextKey = if (movie.body()?.results?.isEmpty() == true) null else movie.body()?.page!! + 1
                )

            }

        }catch (exception:IOException){
            return LoadResult.Error(exception)

        }
        catch (exception:Exception){
            return LoadResult.Error(exception)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}