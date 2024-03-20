package com.example.chat_app.data.repository

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.example.chat_app.data.dto.MessageDto
import com.example.chat_app.data.mapper.toMessage
import com.example.chat_app.data.remote.ChatApi
import com.example.chat_app.domain.model.Message
import com.example.chat_app.domain.model.MessageCreate
import com.example.chat_app.domain.result.SendMediaResult
import com.example.chat_app.util.Constants.USER_ID
import com.example.chat_app.util.Time
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class ChatRepository(
    private val api: ChatApi,
    private val ws: WebSocket,
    private val listener: WebSocketListener,
    private val gson: Gson,
    private val prefs: SharedPreferences
) {


    suspend fun getChat(chatId: String, page: Int, pageSize: Int): List<Message> =
        withContext(Dispatchers.IO) {
            val messagesDto = api.getChat(chatId, page, pageSize)
            val messages: ArrayList<Message> = arrayListOf()

            for (messageDto: MessageDto in messagesDto) {
                val message = messageDto.toMessage()
                messages.add(message)
            }

            messages
        }

    suspend fun sendMessage(messageText: String)  = withContext(Dispatchers.IO){
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

    suspend fun sendMediaItem(chatId: String, mediaItemUri: Uri): SendMediaResult = withContext(Dispatchers.IO) {
        try {
            val mediaItem = mediaItemUri.toFile()
            api.sendMediaItem(
                chatId = chatId,
                mediaItem = MultipartBody.Part.createFormData(
                    "mediaItem",
                    mediaItem.name,
                    mediaItem.asRequestBody()
                )
            )

            SendMediaResult.SendCorrectly

        } catch (e: Exception) {

            SendMediaResult.UnknownError

        }

    }


}