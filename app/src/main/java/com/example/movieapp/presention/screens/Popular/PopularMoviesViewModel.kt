package com.example.movieapp.presention.screens.Popular

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.Popular.GetPopularUseCase
import com.example.movieapp.model.Results
import com.example.movieapp.model.SearchResponse
import com.example.movieapp.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase
):ViewModel() {
    var popularMoviesState:MutableStateFlow<PagingData<Results> > = MutableStateFlow(PagingData.empty())
    init {
        getArtWorks()
    }
//    private fun getArtWorks(){
//        viewModelScope.launch {
//            when(val response= getPopularUseCase.invoke()){
//                is UIState.Success -> popularMoviesState.value=UIState.Success(response.data)
//                is UIState.Loading -> popularMoviesState.value=UIState.Loading()
//                is UIState.Error -> popularMoviesState.value=UIState.Error(response.error)
//                is UIState.Empty -> popularMoviesState.value=UIState.Empty(title = response.title)
//            }
//        }
//    }
       private fun getArtWorks(){
           viewModelScope.launch {
               getPopularUseCase.invoke().distinctUntilChanged()
                   .cachedIn(viewModelScope)
                   .collect{
                       popularMoviesState.value = it
                   }
           }

       }

}