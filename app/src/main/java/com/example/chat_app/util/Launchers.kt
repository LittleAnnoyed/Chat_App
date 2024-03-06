package com.example.chat_app.util

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


@Composable
fun pickMediaLauncher(
    dataType: String?,
    setImageUri: (Uri) -> Unit
): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (dataType == Constants.IMAGE_JPEG_TYPE) {
            uri?.let {
                setImageUri(it)
            }
        }
    }

}
