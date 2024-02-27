package com.example.chat_app.data.remote

import com.example.chat_app.data.dto.ChatDto
import com.example.chat_app.data.dto.UserListItemDto
import com.example.chat_app.domain.user.UserListItem
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApi {

    @GET("/get_chats")
    suspend fun getUserChats(
        @Path(value = "userId") userId: String,
        @Path(value = "page") page: Int,
        @Path(value = "pageSize") pageSize: Int
    ): List<ChatDto>

    @GET("/users")
    suspend fun getUsers(
        @Path(value = "keyword") keyword: String,
        @Path(value = "page") page: Int,
        @Path(value = "pageSize") pageSize: Int
    ): List<UserListItemDto>

}