package com.example.movieapp.presention.screens.HomeScreen

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.profile.GetAccountUseCase
import com.example.movieapp.domain.profile.GetSessionUseCase
import com.example.movieapp.domain.profile.GetTokenUseCase
import com.example.movieapp.model.UIState
import com.example.movieapp.model.UserAccount
import com.example.movieapp.model.UserTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val getAccountUseCase: GetAccountUseCase
):ViewModel() {

     val  userTokenState: MutableStateFlow<UIState<UserTokenResponse>> = MutableStateFlow(UIState.Loading())
     val  sessionState: MutableStateFlow<UIState<UserTokenResponse>> = MutableStateFlow(UIState.Loading())
     val  accountState: MutableStateFlow<UIState<UserAccount>> = MutableStateFlow(UIState.Loading())

     fun getUserToken() {
         viewModelScope.launch {
             when (val response = getTokenUseCase.invoke()) {
                 is UIState.Success -> userTokenState.value = UIState.Success(response.data)
                 is UIState.Loading -> userTokenState.value = UIState.Loading()
                 is UIState.Error -> userTokenState.value = UIState.Error(response.error)
                 is UIState.Empty -> userTokenState.value = UIState.Empty(title = response.title)
             }
         }
    }
     fun getSession(token:String) {
         viewModelScope.launch {
             when (val response = getSessionUseCase.invoke(token)) {
                 is UIState.Success -> sessionState.value = UIState.Success(response.data)
                 is UIState.Loading -> sessionState.value = UIState.Loading()
                 is UIState.Error -> sessionState.value = UIState.Error(response.error)
                 is UIState.Empty -> sessionState.value = UIState.Empty(title = response.title)
             }
         }
    }
     fun getAccount(session:String) {
         viewModelScope.launch {
             when (val response = getAccountUseCase.invoke(session)) {
                 is UIState.Success -> accountState.value = UIState.Success(response.data)
                 is UIState.Loading -> accountState.value = UIState.Loading()
                 is UIState.Error -> accountState.value = UIState.Error(response.error)
                 is UIState.Empty -> accountState.value = UIState.Empty(title = response.title)
             }
         }
     }
    }

