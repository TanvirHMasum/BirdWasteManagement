package com.birdwastemanagement.app.ui.screens.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.data.remote.api.AuthApi
import com.birdwastemanagement.app.data.remote.dto.ResendOtpRequest
import com.birdwastemanagement.app.data.remote.dto.VerifyOtpRequest
import com.birdwastemanagement.app.domain.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {

    private val _verifyState = MutableStateFlow<UiState<String>>(UiState.Success(""))
    val verifyState: StateFlow<UiState<String>> = _verifyState.asStateFlow()

    private val _resendState = MutableStateFlow<UiState<String>>(UiState.Success(""))
    val resendState: StateFlow<UiState<String>> = _resendState.asStateFlow()

    fun verifyOtp(phone: String, otp: String) {
        viewModelScope.launch {
            _verifyState.value = UiState.Loading
            try {
                val request = VerifyOtpRequest(phone, otp)
                val response = authApi.verifyOtp(request)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _verifyState.value = UiState.Success(body.message ?: "OTP Verified successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        if (errorBody != null) {
                            val parsedError = com.google.gson.Gson().fromJson(errorBody, com.birdwastemanagement.app.data.remote.dto.ErrorResponse::class.java)
                            parsedError.message ?: "Verification failed"
                        } else {
                            "Verification failed"
                        }
                    } catch (e: Exception) {
                        "Verification failed"
                    }
                    _verifyState.value = UiState.Error(errorMessage)
                }
            } catch (e: java.net.UnknownHostException) {
                _verifyState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: java.net.ConnectException) {
                _verifyState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: Exception) {
                _verifyState.value = UiState.Error(e.localizedMessage ?: "Network Error")
            }
        }
    }

    fun resendOtp(phone: String) {
        viewModelScope.launch {
            _resendState.value = UiState.Loading
            try {
                val request = ResendOtpRequest(phone)
                val response = authApi.resendOtp(request)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _resendState.value = UiState.Success(body.message ?: "OTP Resent successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        if (errorBody != null) {
                            val parsedError = com.google.gson.Gson().fromJson(errorBody, com.birdwastemanagement.app.data.remote.dto.ErrorResponse::class.java)
                            parsedError.message ?: "Failed to resend OTP"
                        } else {
                            "Failed to resend OTP"
                        }
                    } catch (e: Exception) {
                        "Failed to resend OTP"
                    }
                    _resendState.value = UiState.Error(errorMessage)
                }
            } catch (e: java.net.UnknownHostException) {
                _resendState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: java.net.ConnectException) {
                _resendState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: Exception) {
                _resendState.value = UiState.Error(e.localizedMessage ?: "Network Error")
            }
        }
    }
}
