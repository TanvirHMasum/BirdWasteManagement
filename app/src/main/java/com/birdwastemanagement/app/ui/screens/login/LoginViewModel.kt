package com.birdwastemanagement.app.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.data.remote.api.AuthApi
import com.birdwastemanagement.app.data.remote.dto.LoginRequest
import com.birdwastemanagement.app.domain.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.birdwastemanagement.app.data.local.preferences.UserPreferences
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authApi: AuthApi
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Success(false))
    val loginState: StateFlow<UiState<Boolean>> = _loginState.asStateFlow()

    fun login(username: String, pass: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            if (username.isBlank() || pass.isBlank()) {
                _loginState.value = UiState.Error("Please enter valid credentials")
                return@launch
            }

            try {
                val request = LoginRequest(identifier = username, password = pass)
                val response = authApi.login(request)
                
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    userPreferences.setAuthToken(loginResponse.token)
                    loginResponse.user?.let { 
                        userPreferences.saveUserData(it) 
                    }
                    userPreferences.setLoggedIn(true)
                    _loginState.value = UiState.Success(true)
                } else {
                    _loginState.value = UiState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = UiState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }

    fun guestLogin() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(true)
            _loginState.value = UiState.Success(true)
        }
    }
}
