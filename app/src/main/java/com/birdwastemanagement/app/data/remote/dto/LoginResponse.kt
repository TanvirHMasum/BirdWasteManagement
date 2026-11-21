package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: LoginData?
)

data class LoginData(
    @SerializedName("token") val token: String,
    @SerializedName("account_type") val accountType: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("dashboard") val dashboard: String?,
    @SerializedName("dashboard_endpoint") val dashboardEndpoint: String?,
    @SerializedName("user") val user: UserDto?
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("customer_uid") val customerUid: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("holding_no") val holdingNo: String?,
    @SerializedName("ward_id") val wardId: Int?,
    @SerializedName("current_balance") val currentBalance: String?
)
