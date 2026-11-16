package com.birdwastemanagement.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("father_or_husband_name") val fatherOrHusbandName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("nid_number") val nidNumber: String,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("division_id") val divisionId: Int,
    @SerializedName("district_id") val districtId: Int,
    @SerializedName("upazila_id") val upazilaId: Int,
    @SerializedName("municipality_id") val municipalityId: Int,
    @SerializedName("ward_id") val wardId: Int,
    @SerializedName("area_id") val areaId: Int,
    @SerializedName("holding_no") val holdingNo: String,
    @SerializedName("road_no") val roadNo: String?,
    @SerializedName("full_address") val fullAddress: String?,
    @SerializedName("service_category_id") val serviceCategoryId: Int,
    @SerializedName("registration_source") val registrationSource: String = "mobile_app",
    @SerializedName("declaration") val declaration: Boolean
)
