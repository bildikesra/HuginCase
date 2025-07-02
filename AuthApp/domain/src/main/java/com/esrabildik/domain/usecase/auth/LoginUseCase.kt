package com.esrabildik.domain.usecase.auth

import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.repository.auth.AuthRepository
import com.esrabildik.domain.util.RootResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email : String,password : String) : Boolean {
        return repository
            .signInWithEmailAndPassword(UserRequest(email,password))
            .first()
            .let {
                it is RootResult.Success
            }
    }
}