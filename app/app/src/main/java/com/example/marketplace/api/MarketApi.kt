package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import okhttp3.RequestBody
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit: String): ProductResponse


    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse
}