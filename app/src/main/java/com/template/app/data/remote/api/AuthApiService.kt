package com.template.app.data.remote.api

import com.template.app.data.remote.dto.LoginRequest
import com.template.app.data.remote.dto.LoginResponse
import com.template.app.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>
}
