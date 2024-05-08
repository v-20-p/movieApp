package com.example.movieapp.domain.profile

import com.example.movieapp.data.repository.AccountRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke()=
        accountRepository.getUserToken()
}