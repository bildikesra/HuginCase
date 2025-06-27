package com.esrabildik.feature.register.state

import com.esrabildik.domain.model.UserResponse
import com.google.firebase.auth.FirebaseUser

data class RegisterUIState (
    val isLoading : Boolean = false,
    val user : UserResponse? = null,
    val error : String? = null
)