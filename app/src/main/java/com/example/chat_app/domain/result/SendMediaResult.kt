package com.example.chat_app.domain.result

sealed class SendMediaResult {
    data object SendCorrectly : SendMediaResult()
    data object UnknownError: SendMediaResult()
}