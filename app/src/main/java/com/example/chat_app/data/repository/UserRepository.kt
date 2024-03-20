package com.example.chat_app.data.repository

import android.content.SharedPreferences
import com.example.chat_app.data.mapper.toChat
import com.example.chat_app.data.mapper.toUserListItem
import com.example.chat_app.data.remote.UserApi
import com.example.chat_app.domain.model.Chat
import com.example.chat_app.domain.user.UserListItem
import com.example.chat_app.util.Constants.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userApi: UserApi,
    private val prefs: SharedPreferences
) {

    suspend fun getUserChats(page: Int, pageSize: Int): List<Chat> = withContext(Dispatchers.IO) {
        val userId = prefs.getString(USER_ID, null)
        val userChatDtos = userApi.getUserChats(
            userId = userId ?: "",
            page = page,
            pageSize = pageSize
        )
        val userChats: ArrayList<Chat> = arrayListOf()

        for (chatDto in userChatDtos) {
            val chat = chatDto.toChat()
            userChats.add(chat)
        }

        userChats
    }

    suspend fun getUsersList(keyword: String, page: Int, pageSize: Int): List<UserListItem> =
        withContext(Dispatchers.IO) {

            val userListItemDtos = userApi.getUsers(keyword, page, pageSize)

            val userListItems: ArrayList<UserListItem> = arrayListOf()

            for (userDto in userListItemDtos) {
                val userListItem = userDto.toUserListItem()
                userListItems.add(userListItem)
            }

            userListItems
        }
}