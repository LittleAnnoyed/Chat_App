package com.example.chat_app.presentation.auth.second

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chat_app.data.repository.AuthRepository
import com.example.chat_app.domain.result.SetUserDataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetUserDataViewModel @Inject constructor(
    authRepo: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SetUserDataState())

    fun onEvent(event: SetUserDataEvent){
        when(event){
            is SetUserDataEvent.OnUsernameChanged -> {
                state = state.copy(username = event.value)
            }
            is SetUserDataEvent.OnUserImageChanged -> {
                state = state.copy(userImage = event.value)
            }
        }
    }

}

data class SetUserDataState(
    val username: String = "",
    val userImageUri: Uri = Uri.EMPTY,
    val userImage: Bitmap? = null
)

sealed class SetUserDataEvent {

    data class OnUsernameChanged(val value: String): SetUserDataEvent()
    data class OnUserImageChanged(val value: Bitmap): SetUserDataEvent()
}