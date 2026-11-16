package com.birdwastemanagement.app.domain.model

enum class BillStatus {
    DUE, PAID
}

data class Customer(
    val id: String,
    val name: String,
    val holdingNo: String,
    val ward: String,
    val mobile: String,
    val isActive: Boolean = true,
    val totalDue: Double = 0.0,
    val greenHeroStreak: String = "Green Hero - On Time for 3 Months"
)

data class Bill(
    val id: String,
    val customerId: String,
    val month: String,
    val year: String,
    val amount: Double,
    val status: BillStatus
)

data class Payment(
    val id: String,
    val customerId: String,
    val receiptNo: String,
    val amount: Double,
    val dateTime: String,
    val paymentMethod: String
)

data class Municipality(
    val id: String,
    val name: String,
    val activeCustomers: Int,
    val collectionRate: String
)

data class MonthlyData(
    val month: String,
    val amount: Float
)

data class DashboardStats(
    val totalCustomers: Int,
    val monthlyCollection: Double,
    val dueAmount: Double,
    val monthlyTrend: List<MonthlyData>
)

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
