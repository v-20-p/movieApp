package com.example.movieapp.presention.screens.searchScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.Search.GetSearchUseCase
import com.example.movieapp.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchUseCase: GetSearchUseCase):ViewModel() {

    var searchState: MutableStateFlow<PagingData<Results>> = MutableStateFlow(PagingData.empty())
    init {
        search("")
    }

      fun search(query:String){
         viewModelScope.launch{

         getSearchUseCase.invoke(query).distinctUntilChanged()
             .cachedIn(viewModelScope).collect{
            searchState.value = it
        }
         }

     }

}