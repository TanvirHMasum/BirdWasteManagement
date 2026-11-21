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
                    val loginData = loginResponse.data
                    
                    if (loginData != null) {
                        userPreferences.setAuthToken(loginData.token)
                        loginData.user?.let { 
                            userPreferences.saveUserData(it) 
                        }
                        userPreferences.setLoggedIn(true)
                        _loginState.value = UiState.Success(true)
                    } else {
                        _loginState.value = UiState.Error(loginResponse.message ?: "Login failed: No data received")
                    }
                } else {
                    _loginState.value = UiState.Error("Login failed: ${response.message()}")
                }
            } catch (e: java.net.UnknownHostException) {
                _loginState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: java.net.ConnectException) {
                _loginState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
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
