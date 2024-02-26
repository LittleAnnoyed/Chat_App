package com.example.chat_app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.data.source.UserPagingSource
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo : UserRepository
) : ViewModel() {


    val pager = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        UserPagingSource(
            userRepo = userRepo
        )
    }.flow.cachedIn(viewModelScope)
}