package com.birdwastemanagement.app.domain.repository

import com.birdwastemanagement.app.domain.model.Bill
import com.birdwastemanagement.app.domain.model.Customer
import com.birdwastemanagement.app.domain.model.DashboardStats
import com.birdwastemanagement.app.domain.model.Municipality
import com.birdwastemanagement.app.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface WasteRepository {
    fun getDashboardStats(): Flow<DashboardStats>
    fun getMunicipalities(): Flow<List<Municipality>>
    fun getCustomers(): Flow<List<Customer>>
    fun getCustomerById(id: String): Flow<Customer?>
    fun getBillsForCustomer(customerId: String): Flow<List<Bill>>
    fun processPayment(customerId: String, amount: Double, paymentMethod: String): Flow<Payment>
}
