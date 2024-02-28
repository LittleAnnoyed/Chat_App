package com.example.chat_app.data.remote

import com.example.chat_app.data.dto.AuthDto
import com.example.chat_app.data.dto.AuthTokenDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


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


    @GET("/set_user_data")
    suspend fun setUserData(
        @Part (value = "user_id") userId: String,
        @Part (value = "username") username: String,
        @Part userImageUri: MultipartBody.Part
    )
}