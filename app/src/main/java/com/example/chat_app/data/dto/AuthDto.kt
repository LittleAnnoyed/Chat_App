package com.example.chat_app.data.dto

data class AuthDto(
    val email: String,
    val password: String
)

data class AuthTokenDto(
    val token: String,
    val userId: String,
    val username: String?,
)
