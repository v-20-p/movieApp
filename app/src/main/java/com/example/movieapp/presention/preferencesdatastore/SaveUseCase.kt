package com.example.movieapp.presention.preferencesdatastore

import javax.inject.Inject

class SaveUseCase @Inject constructor(
    private val dataStore:DatastoreApp
) {
    suspend operator fun invoke(show:Boolean){
        dataStore.save(show = show)
    }
}