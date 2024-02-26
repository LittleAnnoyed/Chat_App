package com.example.chat_app.data.mapper

import com.example.chat_app.data.dto.ChatDto
import com.example.chat_app.domain.model.Chat

fun ChatDto.toChat(): Chat {
    return Chat(name, chatImageUri)
}