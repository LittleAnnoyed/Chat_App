package com.example.chat_app.presentation.find

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.data.source.GetUserChatsPagingSource
import com.example.chat_app.data.source.GetUsersListPagingSource
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FindUserViewModel  @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    val pager = Pager(
        PagingConfig(PAGE_SIZE)
    ){
        GetUsersListPagingSource(userRepo = userRepo)
    }.flow
}