package com.esrabildik.feature.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.usecase.auth.SignUpUsecase
import com.esrabildik.domain.util.RootResult
import com.esrabildik.feature.register.event.RegisterEvent
import com.esrabildik.feature.register.state.RegisterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUsecase : SignUpUsecase
) : ViewModel() {

    private val _uistate = MutableStateFlow(RegisterUIState())
    val uiState :  StateFlow<RegisterUIState> = _uistate

    var password = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set


    fun onEvent(event : RegisterEvent){
    when(event){
        is RegisterEvent.SignUp -> {
            signUp(event.request)
        }
        is RegisterEvent.UpdateEmail -> {
            updateEmail(event.email)
        }
        is  RegisterEvent.UpdatePassword -> {
            updatePassword(event.password)
        }
    }
    }

    private fun updatePassword(passwordNew: String){
        password.value = passwordNew
    }

    private fun updateEmail(emailNew : String){
        email.value = emailNew
    }

    private fun signUp(request : UserRequest){
        viewModelScope.launch {
            _uistate.value = _uistate.value.copy(isLoading = true)
            signUpUsecase(request).collect{ result ->
                when(result){
                    is RootResult.Success -> {
                        _uistate.value = RegisterUIState(user = result.data)
                    }
                    is RootResult.Error -> {
                        _uistate.value = RegisterUIState(error = result.message)
                    }
                    is RootResult.Loading -> {
                        _uistate.value = RegisterUIState(isLoading = true)
                    }
                }
            }
        }
    }
}