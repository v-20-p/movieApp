package com.example.movieapp.domain.Popular

import com.example.movieapp.data.repository.PopularMoviesRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetPopularUseCase @Inject constructor(private  val popularMoviesRepository: PopularMoviesRepository) {
    suspend fun invoke()= popularMoviesRepository.getPopularMovies()
}