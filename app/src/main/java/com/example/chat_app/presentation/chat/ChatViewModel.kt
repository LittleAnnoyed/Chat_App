package com.example.chat_app.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.ChatRepository
import com.example.chat_app.data.source.GetChatPagingSource
import com.example.chat_app.domain.model.MessageCreate
import com.example.chat_app.util.Constants.CHAT_ID
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepo: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: String = checkNotNull(savedStateHandle[CHAT_ID])

    var state by mutableStateOf(ChatState())

    val pager = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ){
        GetChatPagingSource(chatRepo,chatId)
    }.flow


    fun onEvent(event: ChatEvent) {
        when(event) {
            is ChatEvent.OnMessageTextChanged -> {
                state = state.copy(messageText = event.value)
            }
            is ChatEvent.OnMessageSend -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        chatRepo.sendMessage(state.messageText)
    }
}

data class ChatState(
    val messageText: String = "",
)

sealed class ChatEvent {
    data class OnMessageTextChanged(val value: String) : ChatEvent()
    data object OnMessageSend : ChatEvent()
}