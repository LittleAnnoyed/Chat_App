package com.example.chat_app.presentation.group.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.chat_app.data.repository.GroupRepository
import com.example.chat_app.data.repository.UserRepository
import com.example.chat_app.data.source.GetUsersListPagingSource
import com.example.chat_app.domain.result.CreateResult
import com.example.chat_app.util.Constants.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupCreateViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val groupRepo : GroupRepository
) : ViewModel() {

    var state by mutableStateOf(GroupCreateState())

    private val resultChannel = Channel<CreateResult>()
    val createResult = resultChannel.receiveAsFlow()

    val pager = Pager(
        PagingConfig(PAGE_SIZE)
    ) {
        GetUsersListPagingSource(userRepo = userRepo, keyword = state.keyword)
    }.flow

    fun onEvent(event: GroupCreateEvent) {
        when (event) {
            is GroupCreateEvent.OnSearchBarTextChanged -> {
                state = state.copy(keyword = event.value)
            }

            is GroupCreateEvent.ChangeCheckBoxValue -> {
                changeCheckBoxValue()
            }

            is GroupCreateEvent.AddGroupCreate -> {
                addUserToGroup(event.value)
            }

            is GroupCreateEvent.RemoveFromGroupCreate -> {
                removeUserFromGroup(event.value)
            }

            is GroupCreateEvent.OpenCreateGroupDialogCreate -> {
                state = state.copy(createGroupAlertDialog = true)
            }

            is GroupCreateEvent.CloseCreateGroupDialogCreate -> {
                state = state.copy(createGroupAlertDialog = false)
            }

            is GroupCreateEvent.OnGroupCreateNameChanged -> {
                state = state.copy(groupName = event.value)
            }

            is GroupCreateEvent.OnGroupCreateImageChanged -> {
                state = state.copy(groupImageUri = event.value)
            }

            is GroupCreateEvent.CreateGroupCreate -> {
                createGroup()
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
        state.groupMembers.add(userId)
    }

    private fun removeUserFromGroup(userId: String) {
        state.groupMembers.remove(userId)
    }

    private fun createGroup() {
        viewModelScope.launch {
            val result = groupRepo.createGroup(
                groupName = state.groupName,
                groupImage = state.groupImageUri.toFile(),
                groupMembers = state.groupMembers
            )
            resultChannel.send(result)
        }
    }

}

data class GroupCreateState(
    val keyword: String = "",
    val isChosen: Boolean = false,
    val groupMembers: ArrayList<String> = arrayListOf(),
    val groupName: String = "",
    val groupImageUri: Uri = Uri.EMPTY,
    val createGroupAlertDialog: Boolean = false
)

sealed class GroupCreateEvent {
    data class OnSearchBarTextChanged(val value: String) : GroupCreateEvent()
    data class AddGroupCreate(val value: String) : GroupCreateEvent()
    data class RemoveFromGroupCreate(val value: String) : GroupCreateEvent()
    data class ChangeCheckBoxValue(val value: Boolean) : GroupCreateEvent()
    data class OnGroupCreateNameChanged(val value: String) : GroupCreateEvent()
    data class OnGroupCreateImageChanged(val value: Uri) : GroupCreateEvent()
    data object OpenCreateGroupDialogCreate : GroupCreateEvent()
    data object CloseCreateGroupDialogCreate : GroupCreateEvent()
    data object CreateGroupCreate : GroupCreateEvent()
}