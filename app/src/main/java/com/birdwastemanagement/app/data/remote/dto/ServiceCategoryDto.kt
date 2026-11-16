package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ServiceCategoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("monthly_fee") val monthlyFee: String
)
