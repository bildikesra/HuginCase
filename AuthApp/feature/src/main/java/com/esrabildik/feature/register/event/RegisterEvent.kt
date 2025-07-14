package com.esrabildik.feature.register.event

import com.esrabildik.domain.model.UserRequest

sealed interface RegisterEvent {
    data class SignUp(val request: UserRequest) : RegisterEvent
    data class UpdatePassword(val password : String) : RegisterEvent
    data class UpdateEmail(val email : String) : RegisterEvent
}