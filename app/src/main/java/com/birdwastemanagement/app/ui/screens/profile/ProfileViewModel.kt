package com.birdwastemanagement.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.birdwastemanagement.app.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import com.birdwastemanagement.app.data.local.preferences.UserPreferences
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val userProfile: StateFlow<Customer> = userPreferences.userProfileFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Customer(
            id = "BWM-00000",
            name = "Guest User",
            holdingNo = "",
            ward = "",
            mobile = ""
        )
    )

    fun logout() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(false)
        }
    }
}
