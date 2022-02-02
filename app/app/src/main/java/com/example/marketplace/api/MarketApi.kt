package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import okhttp3.RequestBody
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse
}