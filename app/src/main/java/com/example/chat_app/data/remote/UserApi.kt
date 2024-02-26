package com.example.chat_app.data.remote

import com.example.chat_app.data.dto.ChatDto
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApi {

    @GET("/get_chats")
    suspend fun getUserChats(
        @Path(value = "userId") userId: String,
        @Path(value = "page") page: Int,
        @Path(value = "pageSize") pageSize: Int
    ): List<ChatDto>

}