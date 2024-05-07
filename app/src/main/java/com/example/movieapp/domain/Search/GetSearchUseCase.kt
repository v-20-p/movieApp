package com.example.movieapp.domain.Search

import com.example.movieapp.data.repository.PopularMoviesRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetSearchUseCase @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository ) {
    suspend operator fun invoke(query: String) = popularMoviesRepository.getSearchMovies(query)

}