package com.esrabildik.feature.state

import com.esrabildik.domain.model.UserResponse
import com.google.firebase.auth.FirebaseUser

data class LoginUIState (
    val isLoading : Boolean = false,
    val user : UserResponse? = null,
    val error : String? = null
    )