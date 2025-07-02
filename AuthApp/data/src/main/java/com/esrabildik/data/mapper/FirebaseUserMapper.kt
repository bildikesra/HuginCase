package com.esrabildik.data.mapper

import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.model.UserResponse
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser.toDomainUserRequest() : UserRequest {
    return UserRequest(email = this.email ?: "",
        password = "")
}


fun FirebaseUser.toDomainUserResponse() : UserResponse {
    return UserResponse(
        userId = this.uid,
        email = this.email ?: "")
}