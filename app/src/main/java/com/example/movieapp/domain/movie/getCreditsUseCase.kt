package com.example.movieapp.domain.movie

import com.example.movieapp.data.repository.MovieDetailsRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetCreditsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository) {

    suspend operator fun invoke(movieId: Int?) = movieDetailsRepository.getCredits(movieId)

}