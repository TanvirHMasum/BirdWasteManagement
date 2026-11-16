package com.birdwastemanagement.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val holdingNo: String,
    val ward: String,
    val mobile: String,
    val isActive: Boolean,
    val totalDue: Double
)
