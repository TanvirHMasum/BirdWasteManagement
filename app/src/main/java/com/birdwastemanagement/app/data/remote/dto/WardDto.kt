package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WardDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ward_no") val wardNo: String
)
