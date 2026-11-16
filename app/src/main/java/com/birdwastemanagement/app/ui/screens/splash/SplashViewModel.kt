package com.birdwastemanagement.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.data.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isLoading: Boolean = true,
    val isFirstTime: Boolean = true,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val uiState: StateFlow<SplashUiState> = combine(
        userPreferences.isFirstTime,
        userPreferences.isLoggedIn
    ) { isFirstTime, isLoggedIn ->
        SplashUiState(
            isLoading = false,
            isFirstTime = isFirstTime,
            isLoggedIn = isLoggedIn
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashUiState()
    )

    fun onGetStartedClicked() {
        viewModelScope.launch {
            userPreferences.setFirstTime(false)
        }
    }
}
