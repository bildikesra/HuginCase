package com.esrabildik.domain.repository

import com.esrabildik.domain.model.UserResponse
import com.esrabildik.domain.util.RootResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signInWithEmailAndPassword(email : String, password : String) : Flow<RootResult<UserResponse>>
}

