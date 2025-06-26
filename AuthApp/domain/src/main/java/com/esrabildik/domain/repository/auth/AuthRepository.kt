package com.esrabildik.domain.repository.auth

import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.model.UserResponse
import com.esrabildik.domain.util.RootResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signInWithEmailAndPassword(request: UserRequest) : Flow<RootResult<UserResponse>>
    fun signUpWithEmailAndPassword(request : UserRequest) : Flow<RootResult<UserResponse>>
}

