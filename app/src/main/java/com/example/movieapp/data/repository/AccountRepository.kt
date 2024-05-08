package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.MovieApi
import com.example.movieapp.model.UIState
import com.example.movieapp.model.UserAccount
import com.example.movieapp.model.UserTokenResponse
import javax.inject.Inject

class AccountRepository @Inject constructor( private val movieApi: MovieApi) {

    suspend fun getUserToken(): UIState<UserTokenResponse> {
        return try {
            val response = movieApi.getToken()
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }
    suspend fun getSessionId(requestToken: String): UIState<UserTokenResponse> {
        return try {
            val response = movieApi.getSession(requestToken=requestToken)
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }
    suspend fun getAccountId(session_id: String): UIState<UserAccount> {
        return try {
            val response = movieApi.getAccount(sessionId=session_id)
            if (response.isSuccessful && response.body() != null) {
                UIState.Success(response.body())
            } else {
                UIState.Empty(message = response.message().toString())
            }
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }
}