package com.example.chat_app.data.remote

import com.example.chat_app.data.dto.AuthDto
import com.example.chat_app.data.dto.AuthTokenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthApi {


    @POST("/signup")
    suspend fun register(
        @Body request: AuthDto
    )

    @POST("/signin")
    suspend fun login(
        @Body request: AuthDto
    ): AuthTokenDto

    @GET("/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )


}