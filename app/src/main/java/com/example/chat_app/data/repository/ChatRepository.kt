package com.example.chat_app.data.repository

import android.content.SharedPreferences
import com.example.chat_app.data.dto.MessageDto
import com.example.chat_app.data.mapper.toMessage
import com.example.chat_app.data.remote.ChatApi
import com.example.chat_app.domain.model.Message
import com.example.chat_app.domain.model.MessageCreate
import com.example.chat_app.util.Constants.USER_ID
import com.example.chat_app.util.Time
import com.google.gson.Gson
import okhttp3.WebSocket


class ChatRepository(
    private val api: ChatApi,
    private val ws: WebSocket,
    private val gson: Gson,
    private val prefs: SharedPreferences
) {


    suspend fun getChat(chatId: String, page: Int, pageSize: Int): List<Message> {

        val messagesDto = api.getChat(chatId, page, pageSize)
        val messages: ArrayList<Message> = arrayListOf()

        for (messageDto: MessageDto in messagesDto) {
            val message = messageDto.toMessage()
            messages.add(message)
        }

        return messages
    }

    fun sendMessage(messageText: String) {
        val userId = prefs.getString(USER_ID, null)
        userId?.let {
            val messageCreate = MessageCreate(
                messageAuthor = it,
                messageText = messageText,
                timestamp = Time.getCurrentTimeInMillis()
            )
            val data = gson.toJson(messageCreate)
            ws.send(data)
        }
    }

}