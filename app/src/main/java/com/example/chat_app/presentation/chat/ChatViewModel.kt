package com.example.chat_app.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.ChatRepository
import com.example.chat_app.data.source.GetChatPagingSource
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepo: ChatRepository
) : ViewModel() {

    val pager = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ){
        GetChatPagingSource(messageRepo)
    }.flow

}