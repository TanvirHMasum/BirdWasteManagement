package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DivisionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String
)
