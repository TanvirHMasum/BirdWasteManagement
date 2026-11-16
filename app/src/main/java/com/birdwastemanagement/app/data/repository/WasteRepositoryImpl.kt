package com.birdwastemanagement.app.data.repository

import com.birdwastemanagement.app.domain.model.Bill
import com.birdwastemanagement.app.domain.model.BillStatus
import com.birdwastemanagement.app.domain.model.Customer
import com.birdwastemanagement.app.domain.model.DashboardStats
import com.birdwastemanagement.app.domain.model.MonthlyData
import com.birdwastemanagement.app.domain.model.Municipality
import com.birdwastemanagement.app.domain.model.Payment
import com.birdwastemanagement.app.domain.repository.WasteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WasteRepositoryImpl @Inject constructor() : WasteRepository {

    private val mockCustomers = listOf(
        Customer(
            id = "CUST-001",
            name = "Rahim Uddin",
            holdingNo = "H-1042",
            ward = "Ward 04",
            mobile = "+880 1712-345678",
            isActive = true,
            totalDue = 200.0,
            greenHeroStreak = "Green Hero - On Time for 3 Months"
        ),
        Customer(
            id = "CUST-002",
            name = "Karim Hossain",
            holdingNo = "H-2031",
            ward = "Ward 02",
            mobile = "+880 1819-876543",
            isActive = true,
            totalDue = 100.0
        ),
        Customer(
            id = "CUST-003",
            name = "Sufia Begum",
            holdingNo = "H-3012",
            ward = "Ward 05",
            mobile = "+880 1911-223344",
            isActive = true,
            totalDue = 0.0
        )
    )

    private val mockBills = listOf(
        Bill(id = "B-2025-06", customerId = "CUST-001", month = "Jun 2025", year = "2025", amount = 100.0, status = BillStatus.DUE),
        Bill(id = "B-2025-05", customerId = "CUST-001", month = "May 2025", year = "2025", amount = 100.0, status = BillStatus.DUE),
        Bill(id = "B-2025-04", customerId = "CUST-001", month = "Apr 2025", year = "2025", amount = 100.0, status = BillStatus.PAID),
        Bill(id = "B-2025-03", customerId = "CUST-001", month = "Mar 2025", year = "2025", amount = 100.0, status = BillStatus.PAID),
        Bill(id = "B-2025-02", customerId = "CUST-001", month = "Feb 2025", year = "2025", amount = 100.0, status = BillStatus.PAID)
    )

    override fun getDashboardStats(): Flow<DashboardStats> = flow {
        emit(
            DashboardStats(
                totalCustomers = 15000,
                monthlyCollection = 1250000.0,
                dueAmount = 275000.0,
                monthlyTrend = listOf(
                    MonthlyData("Jan", 9.5f),
                    MonthlyData("Feb", 10.2f),
                    MonthlyData("Mar", 11.0f),
                    MonthlyData("Apr", 10.8f),
                    MonthlyData("May", 12.0f),
                    MonthlyData("Jun", 12.5f)
                )
            )
        )
    }

    override fun getMunicipalities(): Flow<List<Municipality>> = flow {
        emit(
            listOf(
                Municipality("1", "Narail Municipality", 8500, "92%"),
                Municipality("2", "Madaripur Municipality", 6500, "88%"),
                Municipality("3", "Gopalganj Municipality", 7200, "94%")
            )
        )
    }

    override fun getCustomers(): Flow<List<Customer>> = flow {
        emit(mockCustomers)
    }

    override fun getCustomerById(id: String): Flow<Customer?> = flow {
        emit(mockCustomers.find { it.id == id } ?: mockCustomers.first())
    }

    override fun getBillsForCustomer(customerId: String): Flow<List<Bill>> = flow {
        emit(mockBills.filter { it.customerId == customerId || customerId.isEmpty() })
    }

    override fun processPayment(
        customerId: String,
        amount: Double,
        paymentMethod: String
    ): Flow<Payment> = flow {
        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val payment = Payment(
            id = "PAY-${System.currentTimeMillis() % 100000}",
            customerId = customerId,
            receiptNo = "REC-2025-${(1000..9999).random()}",
            amount = amount,
            dateTime = formatter.format(Date()),
            paymentMethod = paymentMethod
        )
        emit(payment)
    }
}
