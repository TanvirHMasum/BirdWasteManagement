package com.birdwastemanagement.app.data.remote

import com.birdwastemanagement.app.domain.model.Customer
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("customers")
    suspend fun getCustomers(): List<Customer>

    @GET("customers/{id}")
    suspend fun getCustomerById(@Path("id") id: String): Customer
}
