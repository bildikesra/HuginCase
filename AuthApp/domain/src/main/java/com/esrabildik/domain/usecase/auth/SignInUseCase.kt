package com.esrabildik.domain.usecase.auth

import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.repository.auth.AuthRepository


class SignInUseCase(private val repository: AuthRepository) {
    operator fun invoke(request: UserRequest)
    = repository.signInWithEmailAndPassword(request)
}
