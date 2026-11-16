package com.birdwastemanagement.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.domain.model.Customer
import com.birdwastemanagement.app.domain.model.DashboardStats
import com.birdwastemanagement.app.domain.model.Municipality
import com.birdwastemanagement.app.domain.model.UiState
import com.birdwastemanagement.app.domain.repository.WasteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiData(
    val stats: DashboardStats,
    val municipalities: List<Municipality>,
    val customers: List<Customer>
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: WasteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DashboardUiData>> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getDashboardStats()
                .zip(repository.getMunicipalities()) { stats, municipalities ->
                    Pair(stats, municipalities)
                }
                .zip(repository.getCustomers()) { (stats, municipalities), customers ->
                    DashboardUiData(stats, municipalities, customers)
                }
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Failed to load dashboard")
                }
                .collect { data ->
                    _uiState.value = UiState.Success(data)
                }
        }
    }
}
