package com.example.chat_app.presentation.group.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.data.source.GetUsersListPagingSource
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ToGroupViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    var state by mutableStateOf(ToGroupState())

    val pager = Pager(
        PagingConfig(PAGE_SIZE)
    ) {
        GetUsersListPagingSource(userRepo = userRepo, keyword = state.keyword)
    }.flow

    fun onEvent(event: ToGroupEvent) {
        when (event) {
            is ToGroupEvent.OnSearchBarTextChanged -> {
                state = state.copy(keyword = event.value)
            }

            is ToGroupEvent.ChangeCheckBoxValue -> {
                changeCheckBoxValue()
            }

            is ToGroupEvent.AddToGroup -> {
                addUserToGroup(event.value)
            }

            is ToGroupEvent.RemoveFromGroup -> {
                removeUserFromGroup(event.value)
            }

            is ToGroupEvent.CreateGroup -> {

            }
        }
    }

    private fun changeCheckBoxValue() {
        state = if (state.isChosen) {
            state.copy(isChosen = false)
        } else {
            state.copy(isChosen = true)
        }
    }

    private fun addUserToGroup(userId: String) {
        state.chosenUsersList.add(userId)
    }

    private fun removeUserFromGroup(userId: String) {
        state.chosenUsersList.remove(userId)
    }

}

data class ToGroupState(
    val keyword: String = "",
    val isChosen: Boolean = false,
    val chosenUsersList: ArrayList<String> = arrayListOf()
)

sealed class ToGroupEvent {
    data class OnSearchBarTextChanged(val value: String) : ToGroupEvent()
    data class AddToGroup(val value: String) : ToGroupEvent()
    data class RemoveFromGroup(val value: String) : ToGroupEvent()
    data class ChangeCheckBoxValue(val value: Boolean) : ToGroupEvent()
    data object CreateGroup : ToGroupEvent()
}