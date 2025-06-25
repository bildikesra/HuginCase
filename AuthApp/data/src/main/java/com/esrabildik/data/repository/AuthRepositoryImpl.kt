package com.esrabildik.data.repository

import com.esrabildik.data.mapper.toDomainUserResponse
import com.esrabildik.domain.model.UserResponse
import com.esrabildik.domain.repository.AuthRepository
import com.esrabildik.domain.util.RootResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<RootResult<UserResponse>> = flow {

        emit(RootResult.Loading)

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                val userResponse = firebaseUser.toDomainUserResponse()
                emit(RootResult.Success(userResponse))
            } else {
                emit(RootResult.Error("Kullanıcı null döndü"))
            }

        } catch (e: Exception) {
            emit(RootResult.Error(e.message ?: "Bir hata oluştu"))
        }

    }.flowOn(Dispatchers.IO)


}