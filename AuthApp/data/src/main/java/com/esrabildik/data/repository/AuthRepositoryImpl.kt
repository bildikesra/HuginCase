package com.esrabildik.data.repository

import com.esrabildik.data.mapper.toDomainUserResponse
import com.esrabildik.data.utils.NULL_EXCEPTION_MESSAGE
import com.esrabildik.data.utils.UNKNOWN_ERROR_MESSAGE
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.model.UserResponse
import com.esrabildik.domain.repository.auth.AuthRepository
import com.esrabildik.domain.util.RootResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override fun signInWithEmailAndPassword(
        request: UserRequest
    ): Flow<RootResult<UserResponse>> = flow {

        emit(RootResult.Loading)

        val result =
            firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
        val firebaseUser = result.user

        if (firebaseUser != null) {
            val userResponse = firebaseUser.toDomainUserResponse()
            emit(RootResult.Success(userResponse))
        } else {
            emit(RootResult.Error(NULL_EXCEPTION_MESSAGE))
        }

    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(RootResult.Error(UNKNOWN_ERROR_MESSAGE))
        }

    override fun signUpWithEmailAndPassword(request: UserRequest): Flow<RootResult<UserResponse>> =
        flow {
            emit(RootResult.Loading)

            val result =
                firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            val firebaseuser = result.user

            if (firebaseuser != null) {
                val userResponse = firebaseuser.toDomainUserResponse()
                emit(RootResult.Success(userResponse))
            } else {
                emit(RootResult.Error(UNKNOWN_ERROR_MESSAGE))
            }

        }.flowOn(Dispatchers.IO)
}