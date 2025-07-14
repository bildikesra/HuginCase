package com.esrabildik.feature.login.event

import com.esrabildik.domain.model.UserRequest

sealed interface LoginEvent {
    data class SignIn(val request : UserRequest) : LoginEvent
    data class UpdatePassword(val password: String) : LoginEvent
    data class UpdateEmail(val email : String) : LoginEvent

}