package com.example.chat_app.presentation.group.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app.domain.result.CreateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class CreateGroupViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(CreateGroupState())

    private val resultChannel = Channel<CreateResult>()
    val createResult = resultChannel.receiveAsFlow()

    fun onEvent(event: CreateGroupEvent) {
        when(event) {
            is CreateGroupEvent.OnGroupNameChanged -> {
                state = state.copy(groupName = event.value)
            }
            is CreateGroupEvent.OnGroupImageChanged -> {
                state = state.copy(groupImageUri = event.value)
            }
            is CreateGroupEvent.CreateGroup -> {
                createGroup()
            }
        }
    }


}


data class CreateGroupState(
    val groupName: String = "",
    val groupImageUri: Uri = Uri.EMPTY,
)

sealed class CreateGroupEvent {
    data class OnGroupNameChanged(val value: String) : CreateGroupEvent()
    data class OnGroupImageChanged(val value: Uri) : CreateGroupEvent()
    data object CreateGroup : CreateGroupEvent()
}