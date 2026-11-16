package com.birdwastemanagement.app.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.domain.model.Customer
import com.birdwastemanagement.app.domain.model.Payment
import com.birdwastemanagement.app.domain.model.UiState
import com.birdwastemanagement.app.domain.repository.WasteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BillPaymentData(
    val customer: Customer,
    val currentMonthBill: Double = 100.0,
    val previousDue: Double = 100.0,
    val totalPayable: Double = 200.0
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: WasteRepository
) : ViewModel() {

    private val _paymentState = MutableStateFlow<UiState<Payment>>(UiState.Success(Payment("", "", "", 0.0, "", "")))
    val paymentState: StateFlow<UiState<Payment>> = _paymentState.asStateFlow()

    private val _customerData = MutableStateFlow<Customer?>(null)
    val customerData: StateFlow<Customer?> = _customerData.asStateFlow()

    fun loadCustomer(customerId: String) {
        viewModelScope.launch {
            repository.getCustomerById(customerId).collect {
                _customerData.value = it
            }
        }
    }

    fun processPayment(customerId: String, amount: Double, method: String) {
        viewModelScope.launch {
            _paymentState.value = UiState.Loading
            repository.processPayment(customerId, amount, method)
                .catch { e ->
                    _paymentState.value = UiState.Error(e.message ?: "Payment failed")
                }
                .collect { payment ->
                    _paymentState.value = UiState.Success(payment)
                }
        }
    }
}
