package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("customer") val customer: CustomerPhone?,
    @SerializedName("expires_at") val expiresAt: String?
)

data class CustomerPhone(
    @SerializedName("phone") val phone: String
)

data class ErrorResponse(
    @SerializedName("message") val message: String,
    @SerializedName("errors") val errors: Map<String, List<String>>?
)
