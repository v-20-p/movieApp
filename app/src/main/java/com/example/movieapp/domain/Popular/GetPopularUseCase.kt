package com.example.movieapp.domain.Popular

import androidx.paging.PagingData
import com.example.movieapp.data.repository.PopularMoviesRepository
import com.example.movieapp.model.Results
import dagger.Reusable
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


@Reusable
class GetPopularUseCase @Inject constructor(private  val popularMoviesRepository: PopularMoviesRepository) {
//    suspend fun invoke()= popularMoviesRepository.getPopularMovies()
 fun invoke(): Flow<PagingData<Results>> {
    return popularMoviesRepository.getPopularMovies()

}

}