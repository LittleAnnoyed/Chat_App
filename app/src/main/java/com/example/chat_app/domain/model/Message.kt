package com.example.chat_app.domain.model

import java.io.File

data class Message(
    val id: String,
    val timestamp: Long,
    val messageText: String,
    val mediaItemUri: String
)

data class MessageCreate(
    val timestamp: Long,
    val messageText: String,
    val mediaItemUri: File? = null
)