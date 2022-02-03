package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return RetrofitInstance.api.forgotPassword(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token,"300")
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }
}