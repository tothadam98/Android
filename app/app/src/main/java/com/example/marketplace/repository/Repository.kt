package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }
}