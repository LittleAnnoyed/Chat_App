package com.example.chat_app.presentation.auth.second

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app.data.repository.AuthRepository
import com.example.chat_app.domain.result.SetUserDataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetUserDataViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SetUserDataState())

    private val resultChannel = Channel<SetUserDataResult<Unit>>()
    var authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: SetUserDataEvent){
        when(event){
            is SetUserDataEvent.OnUsernameChanged -> {
                state = state.copy(username = event.value)
            }
            is SetUserDataEvent.OnUserImageChanged -> {
                state = state.copy(userImageUri = event.value)
            }
            is SetUserDataEvent.SendUserData -> {
                sendUserData()
            }
        }
    }

    private fun sendUserData() {
        viewModelScope.launch {
            val result = authRepo.setUserData(state.username,state.userImageUri.toFile())
            resultChannel.send(result)
        }
    }
}

data class SetUserDataState(
    val username: String = "",
    val userImageUri: Uri = Uri.EMPTY,
)

sealed class SetUserDataEvent {

    data class OnUsernameChanged(val value: String): SetUserDataEvent()
    data class OnUserImageChanged(val value: Uri): SetUserDataEvent()
    data object SendUserData: SetUserDataEvent()
}