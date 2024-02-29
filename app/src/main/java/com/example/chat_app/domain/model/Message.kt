package com.example.chat_app.domain.model

import java.io.File

data class Message(
    val id: String,
    val messageAuthor: String,
    val timestamp: Long,
    val messageText: String,
    val mediaItemUri: String
)

data class MessageCreate(
    val messageAuthor: String,
    val timestamp: Long,
    val messageText: String,
)