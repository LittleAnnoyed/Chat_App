package com.example.chat_app.data.dto

data class MessageDto(
    val id: String,
    val messageAuthor: String,
    val timestamp: Long,
    val messageText: String,
    val mediaItemUri: String
)