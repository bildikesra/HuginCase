package com.esrabildik.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrabildik.domain.model.UserRequest
import com.esrabildik.domain.model.UserResponse
import com.esrabildik.domain.usecase.SignInUseCase
import com.esrabildik.domain.util.RootResult
import com.esrabildik.feature.state.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    internal fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            signInUseCase(email, password).collect { result ->
                when (result) {
                    is RootResult.Success -> {
                        _uiState.value = LoginUIState(user = result.data)
                    }

                    is RootResult.Error -> {
                        _uiState.value = LoginUIState(error = result.message)
                    }

                    is RootResult.Loading -> {
                        _uiState.value = LoginUIState(isLoading = true)

                    }
                }
            }
        }
    }
}

















