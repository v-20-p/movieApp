package com.example.movieapp.domain.movie

import com.example.movieapp.data.repository.MovieDetailsRepository
import com.example.movieapp.data.repository.PopularMoviesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetMovieDetailsUseCase @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository)
{
    suspend fun invoke(movieId: Int)=movieDetailsRepository.getMovieDetails(movieId)
}
