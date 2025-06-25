package com.esrabildik.domain.usecase

import com.esrabildik.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

// domain/usecase/SignInUseCase.kt
class SignInUseCase(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String)
    = repository.signInWithEmailAndPassword(email, password)
}
