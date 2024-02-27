package com.example.chat_app.data.dto

data class ChatDto(
    val id: String,
    val name: String,
    val chatImageUri: String,
)

data class UserListItemDto(
    val id: String,
    val name: String,
    val userImageUri: String
)