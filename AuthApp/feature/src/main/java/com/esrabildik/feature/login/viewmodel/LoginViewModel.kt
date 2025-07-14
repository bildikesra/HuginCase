package com.esrabildik.feature.login.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.usecase.auth.SignInUseCase
import com.esrabildik.domain.util.RootResult
import com.esrabildik.feature.login.event.LoginEvent
import com.esrabildik.feature.login.state.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    var password = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    fun onEvent(event: LoginEvent) {
        when(event){
            is LoginEvent.SignIn -> {
                signIn(event.request)
            }

            is LoginEvent.UpdatePassword -> {
                updatePassword(event.password)
            }

            is LoginEvent.UpdateEmail -> {
                updateEmail(event.email)
            }
        }
    }


    private fun updatePassword(passwordNew: String){
        password.value = passwordNew
    }

    private fun updateEmail(emailNew : String){
        email.value = emailNew
    }

    private fun signIn(request: UserRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            signInUseCase(request).collect { result ->
                when (result) {
                    is RootResult.Success -> {
                        Log.d("LoginViewModel","Login SUCCESS -> email : ${result.data.email}")
                        _uiState.value = LoginUIState(user = result.data)
                    }

                    is RootResult.Error -> {
                        Log.e("LoginViewModel","Login FAILED -> message: ${result.message}")
                        _uiState.value = LoginUIState(error = result.message)
                    }

                    is RootResult.Loading -> {
                        Log.d("LoginViewModel","Login Loading..")
                        _uiState.value = LoginUIState(isLoading = true)

                    }
                }
            }
        }
    }
}

















