package com.example.chat_app.data.repository

import com.example.chat_app.data.dto.MessageDto
import com.example.chat_app.data.mapper.toMessage
import com.example.chat_app.data.remote.ChatApi
import com.example.chat_app.domain.model.Message


class ChatRepository(
    private val api: ChatApi
) {


    suspend fun getChat(chatId: String,page: Int,pageSize: Int): List<Message> {

        val messagesDto = api.getChat(chatId)
        val messages: ArrayList<Message> = arrayListOf()

        for (messageDto: MessageDto in messagesDto){
            val message = messageDto.toMessage()
            messages.add(message)
        }

        return messages
    }

}