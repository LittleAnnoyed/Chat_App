package com.example.chat_app.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chat_app.data.repository.ChatRepository
import com.example.chat_app.domain.model.Message
import com.example.chat_app.util.Constants.PAGE_SIZE

class GetChatPagingSource(private val messageRepo: ChatRepository, private val chatId: String) :
    PagingSource<Int, Message>() {

    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        return try {

            val page = params.key ?: 1
            val response = messageRepo.getChat(chatId,page,PAGE_SIZE)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}