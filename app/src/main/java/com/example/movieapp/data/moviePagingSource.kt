package com.example.movieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.dao.MovieDao
import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.MovieTable
import com.example.movieapp.model.Results
import okio.IOException

class MoviePagingSource (
    private val movieApi: MovieApi,
    private val query: String? = null,
    private val movieDao: MovieDao
):PagingSource<Int,Results>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
            val cachedMovie= movieDao.getCacheMovies()
        return try {
            val correntPage = params.key ?: 1

            if (query == null) {
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

                val moviesList= movie.body()?.results?.map {
                    MovieTable(
                        id = it.id,
                        title = it.title,
                        page = correntPage,
                        backdropPath= it.backdropPath,
                        posterPath = it.posterPath,

                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage,


                    )
                }
                movieDao.insertMovies(moviesList.orEmpty())
                val pagesToKeep= listOf(correntPage,correntPage+1,correntPage-1)
                movieDao.deleteMovieNotInPage(pagesToKeep)

                LoadResult.Page(
                    data = movie.body()?.results.orEmpty(),
                    prevKey = if (correntPage == 1) null else correntPage - 1,
                    nextKey = if (movie.body()?.results?.isEmpty() == true) null else movie.body()?.page!! + 1
                )




            }

        }catch (exception:Exception){

            LoadResult.Page(
                data = cachedMovie.map {
                    Results(
                        adult = false,
                        backdropPath = it.backdropPath,
                        id = it.id,
                        originalLanguage = "",
                        originalTitle = "",
                        overview = "",
                        popularity = 0.0,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        title = it.title,
                        video = false,
                        voteAverage = it.voteAverage,
                        voteCount = 0

                    )
                },
                prevKey = null,
                nextKey = null
            )
//            return LoadResult.Error(exception)

        }
        catch (exception:Exception){
            return LoadResult.Error(exception)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition
    }

}