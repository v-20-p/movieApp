package com.example.movieapp.domain.profile

import com.example.movieapp.data.repository.AccountRepository
import javax.inject.Inject

class GetSessionUseCase  @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(session:String) = accountRepository.getSessionId(session)


}