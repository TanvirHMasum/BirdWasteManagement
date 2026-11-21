package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("otp") val otp: String
)

data class ResendOtpRequest(
    @SerializedName("phone") val phone: String
)

data class BaseResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String?
)
