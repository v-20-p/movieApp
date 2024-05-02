package com.example.movieapp.presention.screens.Popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.presention.navigation.Home
import com.example.movieapp.presention.navigation.onBoarding
import com.example.movieapp.presention.preferencesdatastore.DatastoreApp
import com.example.movieapp.presention.preferencesdatastore.ReadSafeUseCase
import com.example.movieapp.presention.preferencesdatastore.SaveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val saveUseCase: SaveUseCase,
    private val readSafeUseCase: ReadSafeUseCase
) : ViewModel() {
    val onBoardingCompleted = MutableStateFlow(false)
    var StartDestination= onBoarding.route

    init {
        getBoardingState()
    }


    private  fun getBoardingState(){
        viewModelScope.launch {
            onBoardingCompleted.value=readSafeUseCase().stateIn(viewModelScope).value
            StartDestination =if (onBoardingCompleted.value) Home.route else onBoarding.route

        }


    }
      fun saveBoardingState(show:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            saveUseCase(show)
        }


    }

}
