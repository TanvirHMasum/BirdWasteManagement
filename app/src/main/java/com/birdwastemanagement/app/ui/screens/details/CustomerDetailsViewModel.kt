package com.birdwastemanagement.app.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.domain.model.Bill
import com.birdwastemanagement.app.domain.model.Customer
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

data class CustomerDetailsUiData(
    val customer: Customer,
    val bills: List<Bill>
)

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val repository: WasteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<CustomerDetailsUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<CustomerDetailsUiData>> = _uiState.asStateFlow()

    fun loadCustomerDetails(customerId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getCustomerById(customerId)
                .zip(repository.getBillsForCustomer(customerId)) { customer, bills ->
                    if (customer != null) {
                        CustomerDetailsUiData(customer, bills)
                    } else {
                        null
                    }
                }
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error loading customer details")
                }
                .collect { data ->
                    if (data != null) {
                        _uiState.value = UiState.Success(data)
                    } else {
                        _uiState.value = UiState.Error("Customer not found")
                    }
                }
        }
    }
}
