package com.birdwastemanagement.app.data.remote.api

import com.birdwastemanagement.app.data.remote.dto.DivisionDto
import com.birdwastemanagement.app.data.remote.dto.DistrictDto
import com.birdwastemanagement.app.data.remote.dto.UpazilaDto
import com.birdwastemanagement.app.data.remote.dto.MunicipalityDto
import com.birdwastemanagement.app.data.remote.dto.WardDto
import com.birdwastemanagement.app.data.remote.dto.AreaDto
import com.birdwastemanagement.app.data.remote.dto.ServiceCategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("locations/divisions")
    suspend fun getDivisions(): Response<List<DivisionDto>>

    @GET("locations/districts")
    suspend fun getDistricts(@Query("division_id") divisionId: Int): Response<List<DistrictDto>>

    @GET("locations/upazilas")
    suspend fun getUpazilas(@Query("district_id") districtId: Int): Response<List<UpazilaDto>>

    @GET("locations/municipalities")
    suspend fun getMunicipalities(@Query("upazila_id") upazilaId: Int): Response<List<MunicipalityDto>>

    @GET("locations/wards")
    suspend fun getWards(@Query("municipality_id") municipalityId: Int): Response<List<WardDto>>

    @GET("locations/areas")
    suspend fun getAreas(@Query("ward_id") wardId: Int): Response<List<AreaDto>>

    @GET("service-categories")
    suspend fun getServiceCategories(): Response<List<ServiceCategoryDto>>
}
