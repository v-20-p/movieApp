package com.example.movieapp.presention.screens.MovieDetailsScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.Popular.GetPopularUseCase
import com.example.movieapp.domain.movie.GetMovieDetailsUseCase
import com.example.movieapp.model.MovieDetailsRepo
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    var movieDetailsState: MutableState<UIState<MovieDetailsRepo>> = mutableStateOf(UIState.Loading())
     fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            val response = getMovieDetailsUseCase.invoke(id)
            when (response) {
                is UIState.Success -> movieDetailsState.value= UIState.Success(response.data)
                is UIState.Loading -> movieDetailsState.value= UIState.Loading()
                is UIState.Error -> movieDetailsState.value= UIState.Error(response.error)
                is UIState.Empty -> movieDetailsState.value= UIState.Empty(title = response.title)
            }
        }
    }
}
