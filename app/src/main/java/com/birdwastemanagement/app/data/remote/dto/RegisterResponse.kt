package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: RegisterData?
)

data class RegisterData(
    @SerializedName("phone") val phone: String,
    @SerializedName("expires_at") val expiresAt: String?
)

data class ErrorResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: Map<String, List<String>>?
)
