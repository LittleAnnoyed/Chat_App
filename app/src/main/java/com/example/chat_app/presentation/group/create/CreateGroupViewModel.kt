package com.example.chat_app.presentation.group.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class CreateGroupViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(CreateGroupState())

}

data class CreateGroupState(
    val groupImageUri: Uri = Uri.EMPTY,
)