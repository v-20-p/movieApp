package com.example.movieapp.presention.screens.MovieDetailsScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.Popular.GetPopularUseCase
import com.example.movieapp.domain.movie.GetCreditsUseCase
import com.example.movieapp.domain.movie.GetMovieDetailsUseCase
import com.example.movieapp.model.CreditsRepo
import com.example.movieapp.model.MovieDetailsRepo
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase
) : ViewModel() {

    var movieDetailsState: MutableState<UIState<MovieDetailsRepo>> = mutableStateOf(UIState.Loading())
    var creditsState : MutableState<UIState<CreditsRepo>> = mutableStateOf(UIState.Loading())

     fun getMovieDetails(id: Int?) {
        viewModelScope.launch {
            when(val response = getMovieDetailsUseCase.invoke(id)) {
                is UIState.Success -> movieDetailsState.value= UIState.Success(response.data)
                is UIState.Loading -> movieDetailsState.value= UIState.Loading()
                is UIState.Error -> movieDetailsState.value= UIState.Error(response.error)
                is UIState.Empty -> movieDetailsState.value= UIState.Empty(title = response.title)
            }
        }
    }

    fun getCredits(movieId:Int?) {
        viewModelScope.launch {
            when (val response = getCreditsUseCase.invoke(movieId)) {
                is UIState.Success -> creditsState.value = UIState.Success(response.data)
                is UIState.Loading -> creditsState.value = UIState.Loading()
                is UIState.Error -> creditsState.value = UIState.Error(response.error)
                is UIState.Empty -> creditsState.value = UIState.Empty(title = response.title)
            }
        }
    }
}
