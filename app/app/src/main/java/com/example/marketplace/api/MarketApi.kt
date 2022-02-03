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

    @POST(Constants.PRODUCTS_REMOVE_URL)
    suspend fun removeProduct(@Header("token") token: String, @Query("product_id") product_id : String) : ProductRemoveResponse


    @GET(Constants.GET_ORDER_URL)
    suspend fun getOrders(@Header("token") token: String, @Header("limit") limit: String): OrderResponse

    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse
}