package com.example.chat_app.presentation.chat

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.ChatRepository
import com.example.chat_app.data.source.GetChatPagingSource
import com.example.chat_app.domain.model.MessageCreate
import com.example.chat_app.domain.result.SendMediaResult
import com.example.chat_app.util.Constants.CHAT_ID
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepo: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: String = checkNotNull(savedStateHandle[CHAT_ID])

    var state by mutableStateOf(ChatState())

    private val resultChannel = Channel<SendMediaResult>()
    val sendChannel = resultChannel.receiveAsFlow()

    val pager = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        GetChatPagingSource(chatRepo, chatId)
    }.flow


    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnMessageTextChanged -> {
                state = state.copy(messageText = event.value)
            }

            is ChatEvent.SetMediaItem -> {
                state = state.copy(mediaItemUri = event.value)
            }

            is ChatEvent.OnMessageSend -> {
                sendMessage()
            }

            is ChatEvent.SendMediaItem -> {
                sendMediaItem()
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatRepo.sendMessage(state.messageText)
        }
    }

    private fun sendMediaItem() {
        viewModelScope.launch {
            val result = chatRepo.sendMediaItem(chatId, state.mediaItemUri)
            resultChannel.send(result)
        }
    }
}

data class ChatState(
    val messageText: String = "",
    val mediaItemUri: Uri = Uri.EMPTY
)

sealed class ChatEvent {
    data class OnMessageTextChanged(val value: String) : ChatEvent()
    data class SetMediaItem(val value: Uri) : ChatEvent()
    data object SendMediaItem : ChatEvent()
    data object OnMessageSend : ChatEvent()
}