package com.example.chat_app.presentation.auth.second

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetUserDataViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SetUserDataState())


    fun onEvent(event: SetUserDataEvent){
        when(event){
            is SetUserDataEvent.OnUsernameChanged -> {
                state = state.copy(username = event.value)
            }
            is SetUserDataEvent.OnUserImageChanged -> {
                state = state.copy(userImageUri = event.value)
            }
            is SetUserDataEvent.SendUserData -> {

            }
        }
    }

    private fun sendUserData() {
        viewModelScope.launch {
            authRepo.setUserData(state.username,state.userImageUri.toFile())
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