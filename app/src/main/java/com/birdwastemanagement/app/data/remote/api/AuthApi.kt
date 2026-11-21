package com.birdwastemanagement.app.data.remote.api

import com.birdwastemanagement.app.data.remote.dto.LoginRequest
import com.birdwastemanagement.app.data.remote.dto.LoginResponse
import com.birdwastemanagement.app.data.remote.dto.RegisterRequest
import com.birdwastemanagement.app.data.remote.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("v1/auth/verify-registration-otp")
    suspend fun verifyOtp(@Body request: com.birdwastemanagement.app.data.remote.dto.VerifyOtpRequest): Response<com.birdwastemanagement.app.data.remote.dto.BaseResponse>

    @POST("v1/auth/resend-registration-otp")
    suspend fun resendOtp(@Body request: com.birdwastemanagement.app.data.remote.dto.ResendOtpRequest): Response<com.birdwastemanagement.app.data.remote.dto.BaseResponse>
}
