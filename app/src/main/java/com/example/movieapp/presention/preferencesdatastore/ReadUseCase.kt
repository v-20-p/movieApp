package com.example.movieapp.presention.preferencesdatastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadSafeUseCase @Inject constructor(
    private val dataStore:DatastoreApp
) {
    suspend operator fun invoke():Flow<Boolean> {
        return dataStore.read()
    }

}

