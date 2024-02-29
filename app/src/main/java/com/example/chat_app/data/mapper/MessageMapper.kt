package com.example.chat_app.data.mapper

import com.example.chat_app.data.dto.MessageDto
import com.example.chat_app.domain.model.Message

fun MessageDto.toMessage() : Message {
    return Message(
        id = id,
        messageAuthor = messageAuthor,
        timestamp = timestamp,
        messageText = messageText,
        mediaItemUri = mediaItemUri
    )
}