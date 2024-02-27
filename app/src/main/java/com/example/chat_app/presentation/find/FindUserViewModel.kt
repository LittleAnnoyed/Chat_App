package com.example.chat_app.presentation.find

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.data.source.GetUserChatsPagingSource
import com.example.chat_app.data.source.GetUsersListPagingSource
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class FindUserViewModel  @Inject constructor(
    private val userRepo: UserRepository
): ViewModel() {

    var state by mutableStateOf(FindUserState())

    val pager = Pager(
        PagingConfig(PAGE_SIZE)
    ){
        GetUsersListPagingSource(userRepo = userRepo, keyword = state.keyword)
    }.flow

    fun onEvent(event: FindUserEvent) {
        when(event){
            is FindUserEvent.OnSearchBarTextChanged -> {
                onSearchBarTextChanged(event.value)
            }
        }
    }

    private fun onSearchBarTextChanged(value: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                withTimeout(1000L) {
                    state = state.copy(keyword = value)
                    pager.collect()
                }
            }
        }
    }
}

data class FindUserState(
    val isLoading: Boolean = false,
    val keyword: String = ""
)

sealed class FindUserEvent{
    data class OnSearchBarTextChanged(val value: String) : FindUserEvent()
}