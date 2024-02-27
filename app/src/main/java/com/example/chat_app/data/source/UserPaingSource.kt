package com.example.chat_app.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.domain.model.Chat
import com.example.chat_app.domain.user.UserListItem
import com.example.chat_app.util.Constants.PAGE_SIZE

class GetUserChatsPagingSource(private val userRepo: UserRepository): PagingSource<Int,Chat>() {

    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        return try {
            val page = params.key ?: 1
            val response = userRepo.getUserChats(page,PAGE_SIZE)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page.plus(1) else null
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}

class GetUsersListPagingSource(private val userRepo: UserRepository) : PagingSource<Int,UserListItem>(){
    override fun getRefreshKey(state: PagingState<Int, UserListItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListItem> {
        return try {
            val page = params.key ?: 1
            val response = userRepo.getUsersList(page, PAGE_SIZE)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page.plus(1) else null
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }

}