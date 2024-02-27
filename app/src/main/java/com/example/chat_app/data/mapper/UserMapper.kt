package com.example.chat_app.data.mapper

import com.example.chat_app.data.dto.UserListItemDto
import com.example.chat_app.domain.user.UserListItem

fun UserListItemDto.toUserListItem() : UserListItem {
    return UserListItem(id,name,userImageUri)
}