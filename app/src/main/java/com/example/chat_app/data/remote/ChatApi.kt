package com.example.chat_app.data.remote

import com.example.chat_app.data.dto.MessageDto
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface ChatApi {

    @GET("/chat/{chatId}")
    suspend fun getChat(
        @Path(value = "chatId") chatId: String,
        @Part(value = "page") page: Int,
        @Part(value = "pageSize") pageSize: Int
    ): List<MessageDto>

}