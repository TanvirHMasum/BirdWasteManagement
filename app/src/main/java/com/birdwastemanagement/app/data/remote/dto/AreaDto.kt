package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
